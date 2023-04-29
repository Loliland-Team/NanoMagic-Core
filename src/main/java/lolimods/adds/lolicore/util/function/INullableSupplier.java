package lolimods.adds.lolicore.util.function;

import javax.annotation.Nullable;

@FunctionalInterface
public interface INullableSupplier<T> {
	@Nullable
	T get();
}
