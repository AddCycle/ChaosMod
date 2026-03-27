package net.chaos.chaosmod.network.packets;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketGameMode implements IMessage {
	private int mode = 0;

	public PacketGameMode() {}

	public PacketGameMode(int mode) {
		this.mode = mode;
	}

	public PacketGameMode(GameType type) {
		this.mode = type.getID();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		mode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(mode);
	}

	public static class Handler implements IMessageHandler<PacketGameMode, IMessage> {

		@Override
		public IMessage onMessage(PacketGameMode message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			MinecraftServer server = player.getServer();

			boolean flag = false;

			GameProfile gp = player.getGameProfile();
			if (server.isSinglePlayer()) {
				if (!(server.isSinglePlayer() && server.worlds[0].getWorldInfo().areCommandsAllowed())) flag = true;
			} else {
				if (server.getPlayerList().getOppedPlayers().getEntry(gp) == null) flag = true;
			}
			
			if (flag) return null;

			GameType type = player.interactionManager.getGameType();
			GameType nextType = GameType.getByID(message.mode);

			if (type == nextType)
				return null;

			player.setGameType(nextType);
			player.sendMessage(new TextComponentString("[SERVER] set game mode: " + nextType.getName()));

			return null;
		}
	}
}