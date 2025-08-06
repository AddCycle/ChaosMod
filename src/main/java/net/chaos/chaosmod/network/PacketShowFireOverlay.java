package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketShowFireOverlay implements IMessage {
    private boolean show;
    private int duration;

    public PacketShowFireOverlay() {}

    public PacketShowFireOverlay(boolean show, int duration) {
        this.show = show;
        this.duration = duration;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(show);
        buf.writeInt(duration);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        show = buf.readBoolean();
        duration = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketShowFireOverlay, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketShowFireOverlay message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                player.getEntityData().setBoolean("ShowCustomFireOverlay", message.show);
                player.getEntityData().setInteger("CustomFireOverlayTicks", message.duration);
            });
            return null;
        }
    }
}