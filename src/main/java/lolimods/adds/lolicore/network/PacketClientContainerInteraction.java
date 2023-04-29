package lolimods.adds.lolicore.network;

import io.netty.buffer.ByteBuf;
import lolimods.adds.lolicore.LoliCore;
import lolimods.adds.lolicore.gui.LCContainer;
import lolimods.adds.lolicore.util.data.ByteUtils;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;

@SuppressWarnings("NotNullFieldNotInitialized")
public class PacketClientContainerInteraction implements IMessage {
	private byte[] data;

	public PacketClientContainerInteraction() {
	}

	public PacketClientContainerInteraction(byte[] data) {
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = new byte[buf.readableBytes()];
		buf.readBytes(data);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBytes(data);
	}

	public static class Handler implements IMessageHandler<PacketClientContainerInteraction, IMessage> {
		@Nullable
		@Override
		public IMessage onMessage(PacketClientContainerInteraction message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
				Container cont = ctx.getServerHandler().player.openContainer;
				if (cont == null) {
					LoliCore.LOGGER.warn("No container exists for sync packet from {} ({})", ctx.getServerHandler().player.getName(), ctx.getServerHandler().player.getUniqueID());
					ctx.getServerHandler().player.closeScreen();
				} else if (!(cont instanceof LCContainer)) {
					LoliCore.LOGGER.warn("Container is not from LibNine for sync packet from {} ({})", ctx.getServerHandler().player.getName(), ctx.getServerHandler().player.getUniqueID());
				} else {
					((LCContainer)cont).onClientInteraction(ByteUtils.reader(message.data));
				}
			});
			return null;
		}
	}
}
