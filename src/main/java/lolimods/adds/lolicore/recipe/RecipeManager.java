package lolimods.adds.lolicore.recipe;

import lolimods.adds.lolicore.recipe.IRcp;
import lolimods.adds.lolicore.recipe.IRecipeList;
import lolimods.adds.lolicore.recipe.IRecipeManager;
import lolimods.adds.lolicore.recipe.input.IRcpIn;
import lolimods.adds.lolicore.recipe.output.IRcpOut;
import lolimods.adds.lolicore.recipe.type.SmeltingRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RecipeManager implements IRecipeManager {
	private final Map<Class<?>, RecipeListImpl> recipeLists;

	public RecipeManager() {
		this.recipeLists = new HashMap<>();
		addType(SmeltingRecipe.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, I extends IRcpIn<T>, O extends IRcpOut<?>, R extends IRcp<T, I, O>> IRecipeList<T, I, O, R> getRecipeList(Class<R> type) {
		return (IRecipeList<T, I, O, R>)recipeLists.get(type);
	}

	@Override
	public void addType(Class<? extends IRcp<?, ?, ?>> type) {
		recipeLists.putIfAbsent(type, new RecipeListImpl());
	}

	@SuppressWarnings("rawtypes")
	private static class RecipeListImpl implements IRecipeList {
		private final Collection<IRcp> recipes;

		RecipeListImpl() {
			this.recipes = new ArrayList<>();
		}

		@SuppressWarnings("unchecked")
		@Nullable
		@Override
		public IRcp findRecipe(Object input) {
			return recipes.stream().filter(r -> r.input().matches(input)).findAny().orElse(null);
		}

		@Override
		public Collection recipes() {
			return recipes;
		}

		@Override
		public void add(IRcp recipe) {
			recipes.add(recipe);
		}
	}
}
