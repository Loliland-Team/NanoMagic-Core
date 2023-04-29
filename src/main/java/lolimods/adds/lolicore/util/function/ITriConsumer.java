package lolimods.adds.lolicore.util.function;

@FunctionalInterface
public interface ITriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}
