package net.chaos.chaosmod.network.packets;

import java.util.List;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketFishingResult implements IMessage {
	public int score;
	public int hookId;

	public PacketFishingResult() {
	}

	public PacketFishingResult(int score, int hookId) {
		this.score = score;
		this.hookId = hookId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		score = buf.readInt();
		hookId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(score);
		buf.writeInt(hookId);
	}

	public static class PacketFishingResultHandler implements IMessageHandler<PacketFishingResult, IMessage> {

		@Override
		public IMessage onMessage(PacketFishingResult message, MessageContext ctx) {

			EntityPlayer player = ctx.getServerHandler().player;

			player.getServer().addScheduledTask(() ->
			{
				int score = message.score;

				Entity entity = player.world.getEntityByID(message.hookId);
				if (!(entity instanceof EntityFishHook)) return;
				EntityFishHook hook = (EntityFishHook) entity;

				if (score == 3) {
					player.sendMessage(new TextComponentString("Perfect catch!"));
				}

				Random rand = player.world.rand;

				LootContext.Builder builder = new LootContext.Builder((WorldServer) player.world);
				builder.withLuck(player.getLuck() + 0) // TODO : handle luckOfTheSeaEnchant
				.withPlayer(player)
				.withLootedEntity(hook);

				List<ItemStack> loots = player.world.getLootTableManager()
						.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING)
						.generateLootForPools(rand, builder.build());

				Main.getLogger().info("Loots size: " + loots.size());
				if (loots.size() > 0) Main.getLogger().info("Loot: " + loots.get(0).getDisplayName());

				for (ItemStack stack : loots) {
					if (!player.inventory.addItemStackToInventory(stack)) {
			            player.dropItem(stack, false);
			        }
					
					player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
				}
				
				player.inventoryContainer.detectAndSendChanges();
				hook.setDead();
			});

			return null;
		}
	}
}