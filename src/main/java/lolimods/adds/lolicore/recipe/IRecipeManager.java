package lolimods.adds.lolicore.recipe;

import lolimods.adds.lolicore.recipe.IRcp;
import lolimods.adds.lolicore.recipe.IRecipeList;
import lolimods.adds.lolicore.recipe.input.IRcpIn;
import lolimods.adds.lolicore.recipe.output.IRcpOut;

public interface IRecipeManager {
	<T, I extends IRcpIn<T>, O extends IRcpOut<?>, R extends IRcp<T, I, O>> IRecipeList<T, I, O, R> getRecipeList(Class<R> type);

	void addType(Class<? extends IRcp<?, ?, ?>> type);
}
