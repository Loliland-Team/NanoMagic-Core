package lolimods.adds.lolicore.tile;

import lolimods.adds.lolicore.tile.LCTileEntity;
import net.minecraft.util.ITickable;

public abstract class LCTileEntityTicking extends LCTileEntity implements ITickable {
	private boolean shouldDispatchUpdate;
	private boolean initialized;

	public LCTileEntityTicking() {
		this.shouldDispatchUpdate = false;
		this.initialized = false;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized() {
		initialized = true;
	}

	@Override
	protected void setDirty() {
		shouldDispatchUpdate = true;
	}

	@Override
	public void update() {
		if (shouldDispatchUpdate) {
			markDirty();
			dispatchTileUpdate();
			shouldDispatchUpdate = false;
		}
		if (isInitialized()) tick();
	}

	protected abstract void tick();
}
