package lolimods.adds.lolicore.event;

import lolimods.adds.lolicore.tile.LCTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;

public class TileEntityDispatchHandler {
	private final Set<LCTileEntity> toUpdate = new HashSet<>();

	public void queueTileUpdate(LCTileEntity tile) {
		toUpdate.add(tile);
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if (!toUpdate.isEmpty()) {
			for (LCTileEntity tile : toUpdate) {
				World world = tile.getWorld();
				if (world != null && world.getTileEntity(tile.getPos()) == tile) {
					tile.dispatchTileUpdate();
				}
			}
			toUpdate.clear();
		}
	}
}
