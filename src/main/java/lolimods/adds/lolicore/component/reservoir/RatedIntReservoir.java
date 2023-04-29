package lolimods.adds.lolicore.component.reservoir;

import lolimods.adds.lolicore.component.reservoir.DelegatedIntReservoir;
import lolimods.adds.lolicore.component.reservoir.IIntReservoir;

public class RatedIntReservoir extends DelegatedIntReservoir {
	private final int rateInwards;
	private final int rateOutwards;

	public RatedIntReservoir(IIntReservoir backing, int rateInwards, int rateOutwards) {
		super(backing);
		this.rateInwards = rateInwards;
		this.rateOutwards = rateOutwards;
	}

	@Override
	public int draw(int amount, boolean notSimulated) {
		return rateOutwards == -1 ? super.draw(amount, notSimulated) : (rateOutwards == 0 ? 0 : super.draw(Math.min(amount, rateOutwards), notSimulated));
	}

	@Override
	public int offer(int amount, boolean notSimulated) {
		return rateInwards == -1 ? super.offer(amount, notSimulated) : (rateInwards == 0 ? 0 : super.offer(Math.min(amount, rateInwards), notSimulated));
	}
}
