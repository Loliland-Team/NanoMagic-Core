package lolimods.adds.lolicore.util.tuple;

import lolimods.adds.lolicore.util.function.IMultiConsumer;
import lolimods.adds.lolicore.util.function.IMultiFunction;
import net.minecraft.item.ItemStack;

public interface IMultiStack<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
	A getA();

	B getB();

	C getC();

	D getD();

	E getE();

	F getF();

	G getG();

	H getH();

	I getI();

	J getJ();

	K getK();

	L getL();

	M getM();

	N getN();

	O getO();

	P getP();


	int getAmount();

	default void sprexec(IMultiConsumer<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> executor) {
		executor.accept(getA(), getB(), getC(), getD(), getE(), getF(), getG(), getH(), getI(), getJ(), getK(), getL(), getM(), getN(), getO(), getP());
	}

	default <T> T sprcall(IMultiFunction<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, T> executor) {
		return executor.apply(getA(), getB(), getC(), getD(), getE(), getF(), getG(), getH(), getI(), getJ(), getK(), getL(), getM(), getN(), getO(), getP());
	}

	static <A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> IMultiStack<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> of(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p) {
		return new Impl<>(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p);
	}

	class Impl<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> implements IMultiStack<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P> {
		private final A a;
		private final B b;
		private final C c;
		private final D d;
		private final E e;
		private final F f;
		private final G g;
		private final H h;
		private final I i;
		private final J j;
		private final K k;
		private final L l;
		private final M m;
		private final N n;
		private final O o;
		private final P p;

		private Impl(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.e = e;
			this.f = f;
			this.g = g;
			this.h = h;
			this.i = i;
			this.j = j;
			this.k = k;
			this.l = l;
			this.m = m;
			this.n = n;
			this.o = o;
			this.p = p;
		}

		public A getA() {
			return a;
		}

		public B getB() {
			return b;
		}

		public C getC() {
			return c;
		}

		public D getD() {
			return d;
		}

		public E getE() {
			return e;
		}

		public F getF() {
			return f;
		}

		public G getG() {
			return g;
		}

		public H getH() {
			return h;
		}

		public I getI() {
			return i;
		}

		public J getJ() {
			return j;
		}

		public K getK() {
			return k;
		}

		public L getL() {
			return l;
		}

		public M getM() {
			return m;
		}

		public N getN() {
			return n;
		}

		public O getO() {
			return o;
		}

		public P getP() {
			return p;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof IMultiStack)) return false;
			IMultiStack multi = (IMultiStack) o;
			return  multi.getA().equals(a) &&
					multi.getB().equals(b) &&
					multi.getC().equals(c) &&
					multi.getD().equals(d) &&
					multi.getE().equals(e) &&
					multi.getF().equals(f) &&
					multi.getG().equals(g) &&
					multi.getH().equals(h) &&
					multi.getI().equals(i) &&
					multi.getJ().equals(j) &&
					multi.getK().equals(k) &&
					multi.getL().equals(l) &&
					multi.getM().equals(m) &&
					multi.getN().equals(n) &&
					multi.getO().equals(o) &&
					multi.getP().equals(p);
		}

		@Override
		public int hashCode() {
			return a.hashCode() ^
					b.hashCode() ^
					c.hashCode() ^
					d.hashCode() ^
					e.hashCode() ^
					f.hashCode() ^
					g.hashCode() ^
					h.hashCode() ^
					i.hashCode() ^
					j.hashCode() ^
					k.hashCode() ^
					l.hashCode() ^
					m.hashCode() ^
					n.hashCode() ^
					o.hashCode() ^
					p.hashCode();
		}

		public int getAmount() {
			ItemStack a = (ItemStack) getA();
			ItemStack b = (ItemStack) getB();
			ItemStack c = (ItemStack) getC();
			ItemStack d = (ItemStack) getD();
			ItemStack e = (ItemStack) getE();
			ItemStack f = (ItemStack) getF();
			ItemStack g = (ItemStack) getG();
			ItemStack h = (ItemStack) getH();
			ItemStack i = (ItemStack) getI();
			ItemStack j = (ItemStack) getJ();
			ItemStack k = (ItemStack) getK();
			ItemStack l = (ItemStack) getL();
			ItemStack m = (ItemStack) getM();
			ItemStack n = (ItemStack) getN();
			ItemStack o = (ItemStack) getO();
			ItemStack p = (ItemStack) getP();

			int min = 64;
			if (!a.isEmpty()) min = Math.min(min, a.getCount());
			if (!a.isEmpty()) min = Math.min(min, b.getCount());
			if (!c.isEmpty()) min = Math.min(min, c.getCount());
			if (!d.isEmpty()) min = Math.min(min, d.getCount());
			if (!e.isEmpty()) min = Math.min(min, e.getCount());
			if (!f.isEmpty()) min = Math.min(min, f.getCount());
			if (!g.isEmpty()) min = Math.min(min, g.getCount());
			if (!h.isEmpty()) min = Math.min(min, h.getCount());
			if (!i.isEmpty()) min = Math.min(min, i.getCount());
			if (!j.isEmpty()) min = Math.min(min, j.getCount());
			if (!k.isEmpty()) min = Math.min(min, k.getCount());
			if (!l.isEmpty()) min = Math.min(min, l.getCount());
			if (!m.isEmpty()) min = Math.min(min, m.getCount());
			if (!n.isEmpty()) min = Math.min(min, n.getCount());
			if (!o.isEmpty()) min = Math.min(min, o.getCount());
			if (!p.isEmpty()) min = Math.min(min, p.getCount());


			return Math.max(1, min);
		}
	}
}
