package net.chaos.chaosmod.world.events;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBeacon.BeamSegment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitiesDeathEvents {

	@SubscribeEvent
	/*
	 * This event is intended
	 */
	public void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof EntityDragon)) return;
		
		World world = event.getEntity().world;
        if (world.isRemote) return; // only run on server

        EntityDragon dragon = (EntityDragon) event.getEntity();
        
        BlockPos pos = new BlockPos(20, world.getHeight(20, 20), 20);
        world.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState(), 2);
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntityOxoniumChest) {
        	Random rand = new Random();
        	// LootTable table = new LootTable(new LootPool[] {}); just add chaos heart to the loots
        	// ((TileEntityOxoniumChest) tile).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, rand.nextLong());
        	((TileEntityOxoniumChest) tile).setInventorySlotContents(0, new ItemStack(ModItems.CHAOS_HEART));
        	world.setBlockState(pos.down(3), ModBlocks.BEAM_BLOCK.getDefaultState(), 2);
        	// TileEntityBeacon
        }

        /*ItemStack loot = new ItemStack(ModItems.CHAOS_HEART);
        EntityItem drop = new EntityItem(world, 20, world.getHeight(20, 20), 20, loot);
        world.spawnEntity(drop);*/
	}
	
}
