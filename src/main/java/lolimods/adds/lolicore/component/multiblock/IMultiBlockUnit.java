package lolimods.adds.lolicore.component.multiblock;

import lolimods.adds.lolicore.util.world.WorldBlockPos;

public interface IMultiBlockUnit<T extends IMultiBlockUnit<T>> {
	MultiBlockType<T> getMultiBlockType();

	MultiBlockConnectable<T> getMultiBlockConnection();

	WorldBlockPos getWorldPos();
}
