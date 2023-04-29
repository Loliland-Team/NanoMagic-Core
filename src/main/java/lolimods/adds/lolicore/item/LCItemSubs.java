package lolimods.adds.lolicore.item;

import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.item.LCItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class LCItemSubs extends LCItem {
	private final int variantCount;

	public LCItemSubs(String name, int variantCount) {
		super(name);
		setHasSubtypes(true);
		this.variantCount = variantCount;
	}

	@Override
	protected void initModel() {
		for (int i = 0; i < variantCount; i++) {
			LoliCore.PROXY.getRegistrar().queueItemModelReg(this, i, getModelName(i));
		}
	}

	protected String getModelName(int variant) {
		return getInternalName();
	}

	public int getVariantCount() {
		return variantCount;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < variantCount; i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey(stack) + stack.getMetadata();
	}
}
