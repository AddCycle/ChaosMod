package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.items.ItemBiomeCompass;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

/**
 * TODO : finish the logic, writing the resourceLocation inside the itemstack NBT data
 */
@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketBiomeCompass implements IMessage {
	private String biomeRL;

	public PacketBiomeCompass() {}
	
	public PacketBiomeCompass(String biomeRL) {
		this.biomeRL = biomeRL;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		biomeRL = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, biomeRL);
	}
	
	public static class Handler implements IMessageHandler<PacketBiomeCompass, IMessage> {

		@Override
		public IMessage onMessage(PacketBiomeCompass message, MessageContext ctx) {
			String str = message.biomeRL;
			EntityPlayerMP player = ctx.getServerHandler().player;
			ItemStack stack = player.getHeldItemMainhand();
			if (stack.isEmpty()) {
				Main.getLogger().info("no compass held");
				return null;
			}

			NBTTagCompound tag = stack.getOrCreateSubCompound("data");
			tag.setString(ItemBiomeCompass.SEARCHED_BIOME, str);
			tag.setBoolean(ItemBiomeCompass.IS_DIRTY, true);

			return null;
		}
	}
}