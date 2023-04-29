package lolimods.adds.lolicore.util.function;

@FunctionalInterface
public interface IQuadFunction<A, B, C, D, T> {
	T apply(A a, B b, C c, D d);
}
