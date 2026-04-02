package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.events.fisherman.ClientFishingData;
import net.chaos.chaosmod.jobs.gui.fisherman.GuiFishingResult;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
// INFO : there should be only one itemstack usually (TODO : make it support later a List<ItemStack>)
public class PacketFishingLoot implements IMessage {
	public ItemStack loot;
	
	public PacketFishingLoot() {}

	public PacketFishingLoot(ItemStack loot) {
		this.loot = loot;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		loot = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, loot);
	}
	
	public static class Handler implements IMessageHandler<PacketFishingLoot, IMessage> {

		@Override
		public IMessage onMessage(PacketFishingLoot message, MessageContext ctx) {
			ClientFishingData.lastLoot = message.loot;
			Minecraft.getMinecraft().addScheduledTask(() -> {
			    Minecraft.getMinecraft().displayGuiScreen(new GuiFishingResult());
			});
			return null;
		}
	}
}