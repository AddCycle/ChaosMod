package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.blocks.BlockChaosPortal;
import net.chaos.chaosmod.entity.EntityEyeCrystal;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystalBoss;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketTotemEffect;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class EntitiesDeathEvents {

	@SubscribeEvent
	/*
	 * This event is related to the entities deaths
	 */
	public static void onEntityDeath(LivingDeathEvent event) {
		World world = event.getEntity().world;

		handleDragonDeath(event, world);
		handlePlayerDeath(event, world);
	}

	/**
	 * This event contains SuperTotemRelatedEvents
	 */
	private static void handlePlayerDeath(LivingDeathEvent event, World world) {
		if (!(event.getEntity() instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) event.getEntity();
		ItemStack totem = new ItemStack(ModItems.SUPER_TOTEM);
		if (!world.isRemote) {
			if (!player.inventory.hasItemStack(totem))
				return;

			event.setCanceled(true);

			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);

				if (!stack.isEmpty() && stack.getItem() == ModItems.SUPER_TOTEM) {
					stack.shrink(1);
					break;
				}
			}

			player.setHealth(1.0f);
			player.clearActivePotions();
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
			player.world.playSound(null, player.getPosition(), SoundEvents.ITEM_TOTEM_USE, player.getSoundCategory(),
					1.0f, 1.0f);
			PacketManager.network.sendTo(new PacketTotemEffect(ModItems.SUPER_TOTEM), (EntityPlayerMP) player);
		}
	}

	private static void handleDragonDeath(LivingDeathEvent event, World world) {
		if (!(event.getEntity() instanceof EntityDragon) || world.isRemote)
			return;

		BlockPos pos = new BlockPos(3, world.getHeight(3, 3), 3);
		world.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState(), 2);
		TileEntity tile = world.getTileEntity(pos);

		if (tile instanceof TileEntityOxoniumChest) {
			((TileEntityOxoniumChest) tile).setInventorySlotContents(12, new ItemStack(Blocks.DRAGON_EGG));
			((TileEntityOxoniumChest) tile).setInventorySlotContents(13, new ItemStack(ModItems.CHAOS_HEART));
			((TileEntityOxoniumChest) tile).setInventorySlotContents(14, new ItemStack(Blocks.DRAGON_EGG));
			world.setBlockState(pos.down(3), ModBlocks.BEAM_BLOCK.getDefaultState(), 2);
		}

		// FIXME : make a teleporter more practical
		world.setBlockState(pos.up(2), new BlockChaosPortal("second_tp_pos").getDefaultState(), 2);
		BlockPos target = new BlockPos(400, 80, 400); // Change as needed
		generatePlatform(world, ModBlocks.ENDERITE_BRICKS, target, 100);
		BlockPos center_pylon = new BlockPos(455, 81, 460);
		generatePylon(world, ModBlocks.ALLEMANITE_BRICKS, center_pylon, 3, 15);

		generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.north(10), 3, 19);
		generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.south(10), 3, 19);
		generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.east(10), 3, 19);
		generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.west(10), 3, 19);

		EntityEyeCrystalBoss boss = new EntityEyeCrystalBoss(world, 456.5, 98, 461.5);
		world.spawnEntity(boss);
	}

	private static void generatePlatform(World world, Block block, BlockPos center, int width) {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < width; ++j) {
				world.setBlockState(center.add(i, 0, j), block.getDefaultState(), 2);
			}
		}
	}

	private static void generatePylon(World world, Block block, BlockPos center, int width, int height) {
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				for (int k = 0; k < width; k++) {
					world.setBlockState(new MutableBlockPos(center.getX() + j, center.getY() + i, center.getZ() + k),
							block.getDefaultState());
				}
			}
		}
	}

	private static void generateMinionPylon(World world, Block block, BlockPos center, int width, int height) {
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				for (int k = 0; k < width; k++) {
					world.setBlockState(new MutableBlockPos(center.getX() + j, center.getY() + i, center.getZ() + k),
							block.getDefaultState());
				}
			}
		}
		BlockPos pos = center.add(1.5, height + 1, 1.5);
		EntityEyeCrystal minions = new EntityEyeCrystal(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
				20);
		minions.setBeamTarget(new BlockPos(456.5, 98, 461.5).down());
		world.spawnEntity(minions);
	}
}