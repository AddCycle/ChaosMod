package net.chaos.chaosmod.world.events;

import java.util.Random;

import net.chaos.chaosmod.blocks.BlockChaosPortal;
import net.chaos.chaosmod.entity.EntityEyeCrystal;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystalBoss;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitiesDeathEvents {

	@SubscribeEvent
	/*
	 * This event is related to the dragon death
	 */
	public void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof EntityDragon)) return;
		
		World world = event.getEntity().world;
        if (world.isRemote) return; // only run on server
        
        BlockPos pos = new BlockPos(3, world.getHeight(3, 3), 3);
        world.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState(), 2);
        TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntityOxoniumChest) {
        	Random rand = new Random();
        	((TileEntityOxoniumChest) tile).setInventorySlotContents(12, new ItemStack(Blocks.DRAGON_EGG));
        	((TileEntityOxoniumChest) tile).setInventorySlotContents(13, new ItemStack(ModItems.CHAOS_HEART));
        	((TileEntityOxoniumChest) tile).setInventorySlotContents(14, new ItemStack(Blocks.DRAGON_EGG));
        	world.setBlockState(pos.down(3), ModBlocks.BEAM_BLOCK.getDefaultState(), 2);
        }

        // FIXME : make a teleporter more practical
        world.setBlockState(pos.up(2), new BlockChaosPortal("second_tp_pos").getDefaultState(), 2);
        BlockPos target = new BlockPos(400, 80, 400); // Change as needed
        this.generatePlatform(world, ModBlocks.ENDERITE_BRICKS, target, 100);
        BlockPos center_pylon = new BlockPos(455, 81, 460);
        this.generatePylon(world, ModBlocks.ALLEMANITE_BRICKS, center_pylon, 3, 15);
        
        this.generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.north(10), 3, 19);
        this.generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.south(10), 3, 19);
        this.generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.east(10), 3, 19);
        this.generateMinionPylon(world, Blocks.OBSIDIAN, center_pylon.west(10), 3, 19);

        EntityEyeCrystalBoss boss = new EntityEyeCrystalBoss(world, 456.5, 98, 461.5);
        world.spawnEntity(boss);
	}
	
	private void generatePlatform(World world, Block block, BlockPos center, int width) {
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < width; ++j) {
				world.setBlockState(center.add(i, 0, j), block.getDefaultState(), 2);
			}
		}
	}
	
	private void generatePylon(World world, Block block, BlockPos center, int width, int height) {
		// Random rand = new Random();
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				for (int k = 0; k < width; k++) {
					world.setBlockState(new MutableBlockPos(center.getX() + j, center.getY() + i, center.getZ() + k), block.getDefaultState());
				}
			}
		}
	}

	private void generateMinionPylon(World world, Block block, BlockPos center, int width, int height) {
		// Random rand = new Random();
		for (int i = 0; i <= height; i++) {
			for (int j = 0; j < width; j++) {
				for (int k = 0; k < width; k++) {
					world.setBlockState(new MutableBlockPos(center.getX() + j, center.getY() + i, center.getZ() + k), block.getDefaultState());
				}
			}
		}
		BlockPos pos = center.add(1.5, height + 1, 1.5);
        EntityEyeCrystal minions = new EntityEyeCrystal(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20);
        minions.setBeamTarget(new BlockPos(456.5, 98, 461.5).down());
        world.spawnEntity(minions);
	}

	private void generateGateway(BlockPos pos, World world)
    {
        world.playEvent(3000, pos, 0);
        (new WorldGenEndGateway()).generate(world, new Random(), pos);
    }
	
}