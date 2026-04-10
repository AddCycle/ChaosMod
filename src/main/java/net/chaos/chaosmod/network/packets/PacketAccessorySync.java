package net.chaos.chaosmod.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.ClientAccessoryData;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.common.capabilities.accessory.CapabilityAccessory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketAccessorySync implements IMessage {
	private int playerID;
	private NBTTagCompound nbt;
	private ItemStack necklaceStack;

	public PacketAccessorySync() {} // required empty constructor

	public PacketAccessorySync(EntityPlayer player, ItemStack necklace) {
		this.playerID = player.getEntityId();
		IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
		this.nbt = cap != null ? cap.serializeNBT() : new NBTTagCompound();
		this.necklaceStack = necklace;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		this.playerID = pb.readVarInt();
		try {
			this.nbt = pb.readCompoundTag();
			this.necklaceStack = pb.readItemStack();
		} catch (IOException e) {
			e.printStackTrace();
			this.nbt = new NBTTagCompound();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		pb.writeVarInt(playerID);
		pb.writeCompoundTag(nbt);
		pb.writeItemStack(necklaceStack);
	}

	public static class Handler implements IMessageHandler<PacketAccessorySync, IMessage> {
		@Override
		public IMessage onMessage(PacketAccessorySync message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->
			{
				Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.playerID);
				if (!(entity instanceof EntityPlayer))
					return;

				Entity player = (EntityPlayer) entity;
				if (player != null) {
					IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
	                if (cap != null) {
	                    cap.deserializeNBT(message.nbt);
	                }
					ClientAccessoryData.setPlayerNecklace((EntityPlayer) player, message.necklaceStack);
					Main.getLogger().info("[Client] Player {} necklace updated: {}", player.getName(),
							message.necklaceStack.getDisplayName());
				}
			});
			return null;
		}
	}
}