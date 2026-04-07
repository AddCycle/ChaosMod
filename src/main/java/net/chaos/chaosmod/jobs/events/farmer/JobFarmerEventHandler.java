package net.chaos.chaosmod.jobs.events.farmer;

import static net.chaos.chaosmod.jobs.events.JobEventUtils.incrementRelatedMatchingTasks;

import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobFarmerEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "farmer";

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (!(event.getEntity() instanceof EntityPlayer))
			return;

		World world = event.getWorld();
		BlockPos pos = event.getPos();
		int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, event.getItemStack());

		processBlockCropsRightClick(event, world, pos, fortune);
	}

	private static void processBlockCropsRightClick(RightClickBlock event, World world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (!(block instanceof BlockCrops))
			return;

		BlockCrops crop = (BlockCrops) block;
		if (crop.isMaxAge(state)) {
			event.setCanceled(true);
			event.setCancellationResult(EnumActionResult.SUCCESS);
			crop.dropBlockAsItem(world, pos, state, fortune);
			world.setBlockState(pos, crop.withAge(0));

			ResourceLocation id = block.getRegistryName();
			if (id == null)
				return;

			incrementRelatedMatchingTasks(id, TaskType.HARVEST, TargetType.BLOCK, JOB_ID, event.getEntityPlayer());
		}
	}

	@SubscribeEvent
	public static void onHarvestBlockHandler(BlockEvent.BreakEvent event) {
		if (event.getWorld().isRemote)
			return;
		
		Block block = event.getState().getBlock();

		ResourceLocation id = block.getRegistryName();
			if (id == null)
				return;

		incrementRelatedMatchingTasks(id, TaskType.HARVEST, TargetType.BLOCK, JOB_ID, event.getPlayer());
	}
}