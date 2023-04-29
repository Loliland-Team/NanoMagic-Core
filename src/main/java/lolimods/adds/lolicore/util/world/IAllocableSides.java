package lolimods.adds.lolicore.util.world;

import lolimods.adds.lolicore.util.world.BlockSide;

public interface IAllocableSides<E extends Enum<E>> {
	void setFace(BlockSide face, E state);

	E getFace(BlockSide face);
}
