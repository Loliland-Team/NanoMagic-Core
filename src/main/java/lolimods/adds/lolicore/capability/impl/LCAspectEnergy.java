package lolimods.adds.lolicore.capability.impl;

import lolimods.adds.lolicore.component.reservoir.IIntReservoir;
import lolimods.adds.lolicore.util.data.ByteUtils;
import lolimods.adds.lolicore.util.data.ISerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class LCAspectEnergy implements IEnergyStorage, ISerializable {
	private final IIntReservoir reservoir;

	public LCAspectEnergy(IIntReservoir reservoir) {
		this.reservoir = reservoir;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return reservoir.offer(maxReceive, !simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return reservoir.draw(maxExtract, !simulate);
	}

	@Override
	public int getEnergyStored() {
		return reservoir.getQuantity();
	}

	@Override
	public int getMaxEnergyStored() {
		return reservoir.getCapacity();
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public void serNBT(NBTTagCompound tag) {
		reservoir.serNBT(tag);
	}

	@Override
	public void deserNBT(NBTTagCompound tag) {
		reservoir.deserNBT(tag);
	}

	@Override
	public void serBytes(ByteUtils.Writer data) {
		reservoir.serBytes(data);
	}

	@Override
	public void deserBytes(ByteUtils.Reader data) {
		reservoir.deserBytes(data);
	}
}
