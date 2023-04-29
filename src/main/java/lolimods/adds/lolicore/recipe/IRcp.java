package lolimods.adds.lolicore.recipe;

import lolimods.adds.lolicore.recipe.input.IRcpIn;
import lolimods.adds.lolicore.recipe.output.IRcpOut;

public interface IRcp<T, I extends IRcpIn<T>, O extends IRcpOut<?>> {
	I input();

	O mapToOutput(T input);
}
