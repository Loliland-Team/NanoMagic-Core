package lolimods.adds.lolicore.util.function;

import net.minecraftforge.common.capabilities.Capability;

public interface ICapabilityInstanceConsumer {
	<C> void accept(Capability<C> capability, C instance);
}
