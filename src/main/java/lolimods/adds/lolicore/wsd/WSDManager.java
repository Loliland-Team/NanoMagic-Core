package lolimods.adds.lolicore.wsd;

import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.Virtue;
import lolimods.adds.lolicore.wsd.IWSDIdentity;
import lolimods.adds.lolicore.wsd.LCWSD;
import lolimods.adds.lolicore.wsd.WSDGlobalIdentity;
import lolimods.adds.lolicore.wsd.WSDIdentity;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WSDManager {
	private final Virtue virtue;
	private final Map<IWSDIdentity<? extends LCWSD>, Function<World, ? extends LCWSD>> factoryMap;

	public WSDManager(Virtue virtue) {
		this.virtue = virtue;
		this.factoryMap = new HashMap<>();
	}

	public <T extends LCWSD> void register(WSDIdentity<T> identity, Function<World, T> factory) {
		factoryMap.put(identity, factory);
	}

	public <T extends LCWSD> void register(WSDGlobalIdentity<T> identity, Supplier<T> factory) {
		factoryMap.put(identity, w -> factory.get());
	}

	@SuppressWarnings("unchecked")
	public <T extends LCWSD> T get(WSDIdentity<T> identity, World world) {
		return (T)get0(identity, world);
	}

	@SuppressWarnings("unchecked")
	public <T extends LCWSD> T get(WSDGlobalIdentity<T> identity) {
		return (T)get0(identity, LoliCore.PROXY.getAnySidedWorld());
	}

	private LCWSD get0(IWSDIdentity<?> identity, World world) {
		String identifier = getIdentifier(identity);
		LCWSD data = (LCWSD)world.getPerWorldStorage().getOrLoadData(identity.getType(), identifier);
		if (data == null) {
			data = factoryMap.get(identity).apply(world);
			world.getPerWorldStorage().setData(identifier, data);
		}
		return data;
	}

	private String getIdentifier(IWSDIdentity<?> identity) {
		return String.format("%s:%s_%s", virtue.getModId(), identity.getPrefix(), identity.getIdentifier());
	}
}
