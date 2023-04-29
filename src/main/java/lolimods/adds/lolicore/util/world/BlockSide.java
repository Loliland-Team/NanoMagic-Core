package lolimods.adds.lolicore.util.world;

import com.google.common.collect.ImmutableList;
import lolimods.adds.lolicore.constant.NameConst;
import lolimods.adds.lolicore.util.ImpossibilityRealizedException;
import lolimods.adds.lolicore.util.format.ILocalizable;
import net.minecraft.util.EnumFacing;

import java.util.function.UnaryOperator;

public enum BlockSide implements ILocalizable {
	FRONT(f -> f),
	BACK(EnumFacing::getOpposite),
	UP(f -> EnumFacing.UP),
	LEFT(EnumFacing::rotateY),
	DOWN(f -> EnumFacing.DOWN),
	RIGHT(EnumFacing::rotateYCCW);

	public static final ImmutableList<BlockSide> VALUES = ImmutableList.copyOf(values());
	private final UnaryOperator<EnumFacing> transformer;

	BlockSide(UnaryOperator<EnumFacing> transformer) {
		this.transformer = transformer;
	}

	public EnumFacing getDirection(EnumFacing front) {
		return transformer.apply(front);
	}

	public static BlockSide fromDirection(EnumFacing front, EnumFacing face) {
		for (BlockSide side : values()) {
			if (side.getDirection(front) == face) return side;
		}
		throw new ImpossibilityRealizedException();
	}

	@Override
	public String getTranslationKey() {
		switch (this) {
			case FRONT:
				return NameConst.INFO_BLOCK_SIDE_FRONT;
			case BACK:
				return NameConst.INFO_BLOCK_SIDE_BACK;
			case UP:
				return NameConst.INFO_BLOCK_SIDE_UP;
			case LEFT:
				return NameConst.INFO_BLOCK_SIDE_LEFT;
			case DOWN:
				return NameConst.INFO_BLOCK_SIDE_DOWN;
			case RIGHT:
				return NameConst.INFO_BLOCK_SIDE_RIGHT;
		}
		throw new ImpossibilityRealizedException();
	}
}
