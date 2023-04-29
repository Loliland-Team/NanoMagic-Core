package lolimods.adds.lolicore.util.function;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IInventoryObserver {
	void onSlotChanged(int slot, ItemStack original, ItemStack stack);
}
