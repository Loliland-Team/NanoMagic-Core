package lolimods.adds.lolicore.util;

public class ImpossibilityRealizedException extends RuntimeException {
	public ImpossibilityRealizedException() {
		super("Reached impossible state!");
	}

	public ImpossibilityRealizedException(Throwable cause) {
		super("Reached impossible state!", cause);
	}
}
