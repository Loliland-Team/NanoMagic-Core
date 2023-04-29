package lolimods.adds.lolicore.capability.impl;

import lolimods.adds.lolicore.capability.impl.LCAspectFluidHandler;
import lolimods.adds.lolicore.component.reservoir.FluidReservoir;
import lolimods.adds.lolicore.util.data.ISerializable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class LCAspectFluidHandlerItem extends LCAspectFluidHandler implements IFluidHandlerItem, ISerializable {
	private final ItemStack container;

	public LCAspectFluidHandlerItem(ItemStack container, boolean overflowProtection, FluidReservoir... tanks) {
		super(overflowProtection, tanks);
		this.container = container;
	}

	public LCAspectFluidHandlerItem(ItemStack container, boolean overflowProtection, int tankCount, Supplier<FluidReservoir> tankFactory) {
		super(overflowProtection, tankCount, tankFactory);
		this.container = container;
	}

	@Nonnull
	@Override
	public ItemStack getContainer() {
		return container;
	}
}
