package lolimods.adds.lolicore.util.function;

@FunctionalInterface
public interface ITriFunction<A, B, C, T> {
	T apply(A a, B b, C c);
}
