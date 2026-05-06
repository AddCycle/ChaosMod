package net.chaos.chaosmod.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.tileentity.TileEntityJigsaw;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketSyncJigsaw implements IMessage {
	private NBTTagCompound compound;
	
	public PacketSyncJigsaw() {}
	
	public PacketSyncJigsaw(NBTTagCompound compound) {
		this.compound = compound;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		try {
			this.compound = buffer.readCompoundTag();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeCompoundTag(compound);
	}
	
	public static class Handler implements IMessageHandler<PacketSyncJigsaw, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncJigsaw message, MessageContext ctx) {
			WorldServer world = ctx.getServerHandler().player.getServerWorld();
			world.addScheduledTask(() -> {
                TileEntity te = world.getTileEntity(new BlockPos(
                    message.compound.getInteger("x"),
                    message.compound.getInteger("y"),
                    message.compound.getInteger("z")
                ));
                if (te instanceof TileEntityJigsaw) {
                    TileEntityJigsaw tileentityjigsaw = (TileEntityJigsaw) te;
                    tileentityjigsaw.readFromNBT(message.compound);
                    tileentityjigsaw.markDirty();
                    Main.getLogger().info("Packet sync jigsaw received with te at : {}, {}, {}", message.compound.getInteger("x"), message.compound.getInteger("y"), message.compound.getInteger("z"));
                    Main.getLogger().info("pool: {}", message.compound.getString("pool"));
                }
            });

			return null;
		}
	}
}