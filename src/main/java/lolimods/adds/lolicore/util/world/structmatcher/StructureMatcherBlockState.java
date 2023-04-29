package lolimods.adds.lolicore.util.world.structmatcher;

import lolimods.adds.lolicore.util.world.WorldBlockPos;
import lolimods.adds.lolicore.util.world.structmatcher.IStructureMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.function.Predicate;

public class StructureMatcherBlockState implements IStructureMatcher {
	private final Predicate<IBlockState> condition;

	public StructureMatcherBlockState(Predicate<IBlockState> condition) {
		this.condition = condition;
	}

	@Override
	public boolean testStructure(WorldBlockPos basePos, List<Vec3i> components) {
		for (Vec3i pos : components) {
			if (!condition.test(basePos.offset(pos).getBlockState())) {
				return false;
			}
		}
		return true;
	}
}
