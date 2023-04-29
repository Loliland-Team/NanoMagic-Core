package lolimods.adds.lolicore.recipe.type;

import lolimods.adds.lolicore.recipe.IRcp;
import lolimods.adds.lolicore.recipe.input.ItemStackInput;
import lolimods.adds.lolicore.recipe.output.ItemStackOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class SmeltingRecipe implements IRcp<ItemStack, ItemStackInput, ItemStackOutput> {
	private final ItemStackInput input;
	private final ItemStackOutput output;

	public SmeltingRecipe(ItemStack input, ItemStack output) {
		this.input = new ItemStackInput(ItemHandlerHelper.copyStackWithSize(input, 1));
		this.output = new ItemStackOutput(output.copy());
	}

	@Override
	public ItemStackInput input() {
		return input;
	}

	@Override
	public ItemStackOutput mapToOutput(ItemStack input) {
		return output;
	}
}
