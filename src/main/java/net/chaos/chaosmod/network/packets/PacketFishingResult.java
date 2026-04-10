package net.chaos.chaosmod.network.packets;

import java.util.List;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.ItemHandlerHelper;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketFishingResult implements IMessage {
	public int score;
	public int hookId;

	public PacketFishingResult() {}

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

			EntityPlayerMP player = ctx.getServerHandler().player;

			player.getServer().addScheduledTask(() ->
			{
				int score = message.score;

				Entity entity = player.world.getEntityByID(message.hookId);
				if (!(entity instanceof EntityFishHook))
					return;
				EntityFishHook hook = (EntityFishHook) entity;

				if (score == 3) {
					player.sendMessage(new TextComponentString("Perfect catch!"));
				}

				Random rand = player.world.rand;
				ItemStack held = player.getHeldItemMainhand();
				int k = EnchantmentHelper.getFishingLuckBonus(held); // 0-0-3
				// player luck (-1024)-0-1024

				LootContext.Builder builder = new LootContext.Builder((WorldServer) player.world);
				builder.withLuck(player.getLuck() + k + Math.max(0, score - 1)).withPlayer(player)
						.withLootedEntity(hook);

				List<ItemStack> loots = player.world.getLootTableManager()
						.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING)
						.generateLootForPools(rand, builder.build());

				int notInGround = 1; // damage taken
				int inGround = 2; // damage taken
				ItemFishedEvent event = new ItemFishedEvent(loots, notInGround, hook);
				MinecraftForge.EVENT_BUS.post(event);
				if (event.isCanceled()) {
					hook.setDead();
					damageRod(held, notInGround, rand, player);
					return;
				}

				for (ItemStack stack : loots) {
					if (score == 0)
						break;
					int multiplier = Math.max(1, rand.nextInt(Math.max(1, score)));
					stack.setCount(stack.getCount() * multiplier);

					ItemHandlerHelper.giveItemToPlayer(player, stack);
				}

				hook.setDead();
				damageRod(held, notInGround, rand, player);

				player.inventoryContainer.detectAndSendChanges();
			});

			return null;
		}
	}
	
	private static void damageRod(ItemStack held, int rodDamage, Random rand, EntityPlayerMP player) {
	    if (held.isEmpty()) return;

	    // unbreaking/unbreakable
	    if (!held.isItemStackDamageable()) return;

	    if ((held.getMaxDamage() - held.getItemDamage()) - rodDamage <= 0) {
	        held.shrink(1);
	    } else {
	        held.attemptDamageItem(rodDamage, rand, player);
	    }
	}
}