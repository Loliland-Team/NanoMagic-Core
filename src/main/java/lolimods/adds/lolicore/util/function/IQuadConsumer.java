package lolimods.adds.lolicore.util.function;

@FunctionalInterface
public interface IQuadConsumer<A, B, C, D> {
	void accept(A a, B b, C c, D d);
}
