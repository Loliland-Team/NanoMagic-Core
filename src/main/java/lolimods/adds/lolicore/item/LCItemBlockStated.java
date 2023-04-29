package lolimods.adds.lolicore.item;

import lolimods.adds.lolicore.block.LCBlockStated;
import lolimods.adds.lolicore.block.state.VirtualState;
import lolimods.adds.lolicore.item.LCItemBlock;
import net.minecraft.item.ItemStack;

public class LCItemBlockStated extends LCItemBlock {
	private final LCBlockStated block;

	public LCItemBlockStated(LCBlockStated block) {
		super(block);
		this.block = block;
		setHasSubtypes(true);
	}

	public String getModelName(VirtualState state) {
		return getBlock().getInternalName();
	}

	@Override
	public LCBlockStated getBlock() {
		return block;
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
