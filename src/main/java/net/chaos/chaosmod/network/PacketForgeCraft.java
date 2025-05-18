package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketForgeCraft implements IMessage {
    private int craftType;
    private BlockPos pos;

    public PacketForgeCraft() {} // Needed
    public PacketForgeCraft(int craftType, BlockPos pos) {
        this.craftType = craftType;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.craftType = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(craftType);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketForgeCraft, IMessage> {
        @Override
        public IMessage onMessage(PacketForgeCraft message, MessageContext ctx) {
            MinecraftServer server = ctx.getServerHandler().player.getServer();
            server.addScheduledTask(() -> {
                World world = ctx.getServerHandler().player.world;
                TileEntity te = world.getTileEntity(message.pos);
                if (te instanceof TileEntityForge) {
                    ((TileEntityForge) te).startCraft(message.craftType);
                }
            });
            return null;
        }
    }
}