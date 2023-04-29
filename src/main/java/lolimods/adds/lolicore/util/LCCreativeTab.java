package lolimods.adds.lolicore.util;

import lolimods.adds.lolicore.util.LazyConstant;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class LCCreativeTab extends CreativeTabs {
	private final LazyConstant<ItemStack> icon;

	public LCCreativeTab(String name, Supplier<ItemStack> iconFactory) {
		super(name);
		this.icon = new LazyConstant<>(iconFactory);
	}

	@Override
	public ItemStack createIcon() {
		return icon.get();
	}
}
