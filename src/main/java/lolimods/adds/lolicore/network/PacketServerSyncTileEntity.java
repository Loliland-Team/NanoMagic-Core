package lolimods.adds.lolicore.network;

import io.netty.buffer.ByteBuf;
import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.tile.LCTileEntity;
import lolimods.adds.lolicore.util.data.ByteUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

@SuppressWarnings("NotNullFieldNotInitialized")
public class PacketServerSyncTileEntity implements IMessage {
	private int dimension;
	private BlockPos pos;
	private byte[] data;

	public PacketServerSyncTileEntity() {
		// NO-OP
	}

	public PacketServerSyncTileEntity(LCTileEntity tile) {
		this.dimension = tile.getWorld().provider.getDimension();
		this.pos = tile.getPos();
		ByteUtils.Writer writer = ByteUtils.writer();
		tile.serBytes(writer);
		this.data = writer.toArray();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.dimension = buf.readInt();
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		pos = new BlockPos(x, y, z);
		data = new byte[buf.readableBytes()];
		buf.readBytes(data);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimension);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBytes(data);
	}

	public static class Handler implements IMessageHandler<PacketServerSyncTileEntity, IMessage> {
		@Nullable
		@Override
		public IMessage onMessage(PacketServerSyncTileEntity message, MessageContext ctx) {
			World world = LoliCore.PROXY.getDimensionWorld(message.dimension);
			if (world != null) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					TileEntity tile = world.getTileEntity(message.pos);
					if (tile instanceof LCTileEntity) {
						((LCTileEntity)tile).onTileSyncPacket(ByteUtils.reader(message.data));
					} else {
						LoliCore.LOGGER.warn("No tile exists for sync packet at pos {}", message.pos);
					}
				});
			}
			return null;
		}
	}
}
