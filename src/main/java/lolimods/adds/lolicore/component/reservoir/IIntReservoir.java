package lolimods.adds.lolicore.component.reservoir;

import lolimods.adds.lolicore.util.data.ISerializable;
import lolimods.adds.lolicore.util.function.IIntBiConsumer;

public interface IIntReservoir extends ISerializable {
	int getQuantity();

	void setQuantity(int qty);

	default void offsetQuantity(int offset) {
		setQuantity(getQuantity() + offset);
	}

	int getCapacity();

	default int getRemainingCapacity() {
		return getCapacity() - getQuantity();
	}

	int draw(int amount, boolean notSimulated);

	int offer(int amount, boolean notSimulated);

	void onQuantityChange(IIntBiConsumer callback);
}
