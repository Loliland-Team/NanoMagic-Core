package lolimods.adds.lolicore.util.world.structmatcher;

import lolimods.adds.lolicore.util.world.WorldBlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.List;

public interface IStructureMatcher {
	boolean testStructure(WorldBlockPos basePos, List<Vec3i> components);
}
