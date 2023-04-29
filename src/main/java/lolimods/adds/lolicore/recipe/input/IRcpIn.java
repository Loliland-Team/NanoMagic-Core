package lolimods.adds.lolicore.recipe.input;

public interface IRcpIn<T> {
	boolean matches(T input);

	T consume(T input);

	T consume(T input, int count);

	int getAmount();
}
