package lolimods.adds.lolicore.util;

public class Unit {
	public static final Unit INSTANCE = new Unit();

	private Unit() {
	}

	@Override
	public int hashCode() {
		return 0xFEEDBEEF;
	}

	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	@Override
	public boolean equals(Object obj) {
		return obj == INSTANCE;
	}
}
