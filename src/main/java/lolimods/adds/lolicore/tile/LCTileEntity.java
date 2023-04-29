package lolimods.adds.lolicore.tile;

import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.capability.provider.NoopCapabilities;
import lolimods.adds.lolicore.util.LazyConstant;
import lolimods.adds.lolicore.util.data.ByteUtils;
import lolimods.adds.lolicore.util.data.ISerializable;
import lolimods.adds.lolicore.util.data.serialization.DataSerialization;
import lolimods.adds.lolicore.util.world.WorldBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class LCTileEntity extends TileEntity implements ISerializable {
	private final DataSerialization serializer;
	private final LazyConstant<ICapabilityProvider> capabilityBroker;
	@Nullable
	private WorldBlockPos worldPos;
	private boolean requiresSync;

	public LCTileEntity() {
		this.serializer = new DataSerialization(this);
		this.capabilityBroker = new LazyConstant<>(this::initCapabilities);
		this.worldPos = null;
		this.requiresSync = false;
	}

	protected ICapabilityProvider initCapabilities() {
		return new NoopCapabilities();
	}

	protected void markRequiresSync() {
		requiresSync = true;
	}

	public WorldBlockPos getWorldPos() {
		return worldPos == null ? (worldPos = new WorldBlockPos(world, pos)) : worldPos;
	}

	protected void setDirty() {
		if (world != null) {
			markDirty();
			if (!world.isRemote){
				LoliCore.PROXY.getTileEntityDispatcher().queueTileUpdate(this);
			}
		}
	}

	@Override
	public void setPos(BlockPos pos) {
		super.setPos(pos);
		if (world != null) worldPos = new WorldBlockPos(world, pos);
	}

	@Override
	public void setWorld(World world) {
		super.setWorld(world);
		if (pos != null) worldPos = new WorldBlockPos(world, pos);
	}

	public void dispatchTileUpdate() {
		if (requiresSync) LoliCore.PROXY.dispatchTileUpdate(this);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capabilityBroker.get().hasCapability(capability, facing) || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		T aspect = capabilityBroker.get().getCapability(capability, facing);
		return aspect != null ? aspect : super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		worldPos = new WorldBlockPos(world, pos);
		serializer.deserializeNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		serializer.serializeNBT(compound);
		return compound;
	}

	@Override
	public void deserNBT(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void serNBT(NBTTagCompound tag) {
		writeToNBT(tag);
	}

	@Override
	public void serBytes(ByteUtils.Writer data) {
		serializer.serializeBytes(data, true);
	}

	@Override
	public void deserBytes(ByteUtils.Reader data) {
		serializer.deserializeBytes(data, true);
	}

	public void onTileSyncPacket(ByteUtils.Reader data) {
		deserBytes(data);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return requiresSync ? new SPacketUpdateTileEntity(pos, -0, getUpdateTag()) : null;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return serializeNBT();
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}
}
