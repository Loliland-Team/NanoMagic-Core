package lolimods.adds.lolicore.recipe.input;

import lolimods.adds.lolicore.recipe.input.IRcpIn;
import lolimods.adds.lolicore.util.IDisplayableMatcher;
import lolimods.adds.lolicore.util.helper.ItemUtils;
import net.minecraft.item.ItemStack;

public class ItemStackInput implements IRcpIn<ItemStack> {
	private final IDisplayableMatcher<ItemStack> matcher;
	private final int amount;

	public ItemStackInput(ItemStack stack) {
		this.matcher = ItemUtils.matchesWithWildcard(stack);
		this.amount = stack.getCount();
	}

	public ItemStackInput(IDisplayableMatcher<ItemStack> matcher, int amount) {
		this.matcher = matcher;
		this.amount = amount;
	}

	public IDisplayableMatcher<ItemStack> getMatcher() {
		return matcher;
	}

	@Override
	public boolean matches(ItemStack input) {
		return matcher.test(input) && input.getCount() >= amount;
	}

	@Override
	public ItemStack consume(ItemStack input) {
		if (input.getCount() == amount) return ItemStack.EMPTY;
		input.shrink(amount);
		return input;
	}

	@Override
	public ItemStack consume(ItemStack input, int count) {
		if (input.getCount() == count)
			return ItemStack.EMPTY;
		input.shrink(count);
		return input;
	}

	public int getAmount() {
		return amount;
	}
}
