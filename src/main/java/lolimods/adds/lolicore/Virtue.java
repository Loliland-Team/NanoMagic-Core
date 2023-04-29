package lolimods.adds.lolicore;

import lolimods.adds.lolicore.block.LCBlock;
import lolimods.adds.lolicore.component.multiblock.IMultiBlockUnit;
import lolimods.adds.lolicore.component.multiblock.MultiBlockType;
import lolimods.adds.lolicore.gui.LCGuiHandler;
import lolimods.adds.lolicore.item.LCItem;
import lolimods.adds.lolicore.network.PacketClientContainerInteraction;
import lolimods.adds.lolicore.network.PacketServerSyncTileEntity;
import lolimods.adds.lolicore.util.LazyConstant;
import lolimods.adds.lolicore.util.render.TextureResource;
import lolimods.adds.lolicore.wsd.WSDManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Virtue {
	private static final Map<String, Virtue> loadedVirtues = new LinkedHashMap<>();

	public static Virtue forMod(String modId) {
		return loadedVirtues.get(modId);
	}

	private final String modId, modPref;
	@Nullable
	private final CreativeTabs defaultCreativeTab;
	private final LazyConstant<SimpleNetworkWrapper> networkHandler;
	private final LazyConstant<LCGuiHandler> guiHandler;
	private final LazyConstant<WSDManager> wsdManager;
	private boolean usesTileEntities, usesContainers;

	public Virtue(String modId, @Nullable CreativeTabs defaultCreativeTab) {
		this.modId = modId;
		this.modPref = modId + ":";
		this.defaultCreativeTab = defaultCreativeTab;
		this.networkHandler = new LazyConstant<>(() -> NetworkRegistry.INSTANCE.newSimpleChannel(modId));
		this.guiHandler = new LazyConstant<>(() -> {
			LCGuiHandler handler = new LCGuiHandler(this);
			NetworkRegistry.INSTANCE.registerGuiHandler(this, handler);
			return handler;
		});
		this.wsdManager = new LazyConstant<>(() -> new WSDManager(this));
		this.usesTileEntities = this.usesContainers = false;
		loadedVirtues.put(modId, this);
	}

	public Virtue(String modId) {
		this(modId, null);
	}

	void markUsesTileEntities() {
		if (!usesTileEntities) {
			getNetworkHandler().registerMessage(PacketServerSyncTileEntity.Handler.class, PacketServerSyncTileEntity.class, 0, Side.CLIENT);
			usesTileEntities = true;
		}
	}

	void markUsesContainers() {
		if (!usesContainers) {
			getNetworkHandler().registerMessage(PacketClientContainerInteraction.Handler.class, PacketClientContainerInteraction.class, 1, Side.SERVER);
			usesContainers = true;
		}
	}

	public String getModId() {
		return modId;
	}

	@Nullable
	public CreativeTabs getDefaultCreativeTab() {
		return defaultCreativeTab;
	}

	public SimpleNetworkWrapper getNetworkHandler() {
		return networkHandler.get();
	}

	public LCGuiHandler getGuiHandler() {
		return guiHandler.get();
	}

	public WSDManager getWsdManager() {
		return wsdManager.get();
	}

	public String prefix(String suffix) {
		return modPref + suffix;
	}

	public ResourceLocation newResourceLocation(String resource) {
		return new ResourceLocation(modId, resource);
	}

	public ModelResourceLocation newModelResourceLocation(String model, String variant) {
		return new ModelResourceLocation(prefix(model), variant);
	}

	public ModelResourceLocation newModelResourceLocation(String model) {
		return new ModelResourceLocation(prefix(model));
	}

	public TextureResource newTextureResource(String resource, int width, int height) {
		return new TextureResource(newResourceLocation(resource), width, height);
	}

	public SoundEvent newSoundEvent(String key) {
		ResourceLocation id = newResourceLocation(key);
		SoundEvent sound = new SoundEvent(id);
		sound.setRegistryName(id);
		LoliCore.PROXY.getRegistrar().queueSoundEventReg(sound);
		return sound;
	}

	public <T extends IMultiBlockUnit<T>> MultiBlockType<T> newMultiBlockType(String key, int maxSearchDist, Class<T> componentType) {
		return new MultiBlockType<>(newResourceLocation(key), maxSearchDist, componentType);
	}

	public void setCreativeTabFor(LCItem item) {
		if (defaultCreativeTab != null) {
			item.setCreativeTab(defaultCreativeTab);
		}
	}

	public void setCreativeTabFor(LCBlock block) {
		if (defaultCreativeTab != null) {
			block.setCreativeTab(defaultCreativeTab);
		}
	}
}
