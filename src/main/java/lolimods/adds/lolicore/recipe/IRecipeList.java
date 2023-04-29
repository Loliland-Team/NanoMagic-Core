package lolimods.adds.lolicore.recipe;

import lolimods.adds.lolicore.recipe.IRcp;
import lolimods.adds.lolicore.recipe.input.IRcpIn;
import lolimods.adds.lolicore.recipe.output.IRcpOut;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IRecipeList<T, I extends IRcpIn<T>, O extends IRcpOut<?>, R extends IRcp<T, I, O>> {
	@Nullable
	R findRecipe(T input);

	Collection<R> recipes();

	void add(R recipe);
}
