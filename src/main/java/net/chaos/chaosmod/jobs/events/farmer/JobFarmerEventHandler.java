package net.chaos.chaosmod.jobs.events.farmer;

import static net.chaos.chaosmod.jobs.events.JobEventUtils.onHarvestBlock;

import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
// TODO : rn never using the target item/type here inside the jsons
public class JobFarmerEventHandler {
	private static final ResourceLocation DIAMONA = new ResourceLocation(Reference.MATHSMOD, "diamona");
	private static final ResourceLocation HELL_FLOWER = new ResourceLocation(Reference.MATHSMOD, "hell_flower");
	private static final ResourceLocation OXONIUM_CARROTS = new ResourceLocation(Reference.MODID, "oxonium_carrots");
	private static final ResourceLocation WHEAT = new ResourceLocation("wheat");
	private static final ResourceLocation CUSTOM_FLOWERS = new ResourceLocation(Reference.MODID, "custom_flower");

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

			JobEventUtils.onRightClickBlock(event, OXONIUM_CARROTS, blk ->
			{
				incrementTask(event.getEntityPlayer(), "farm_oxonium_carrots");
			});

			JobEventUtils.onRightClickBlock(event, WHEAT, blk ->
			{
				incrementTask(event.getEntityPlayer(), "wheat_addict");
			});
		}
	}

	@SubscribeEvent
	public static void onHarvestBlockHandler(BlockEvent.BreakEvent event) {
		if (event.getWorld().isRemote)
			return;
		
		onHarvestBlock(event, OXONIUM_CARROTS, block ->
		{
			if (!((BlockCrops) block).isMaxAge(event.getState())) return;
			incrementTask(event.getPlayer(), "farm_oxonium_carrots");
		});

		onHarvestBlock(event, DIAMONA, (block) ->
		{
			incrementTask(event.getPlayer(), "gather_diamona");
		});

		onHarvestBlock(event, HELL_FLOWER, (block) ->
		{
			incrementTask(event.getPlayer(), "gather_hell_flower");
		});

		// be careful on custom_flowers as all the variants are allowed here
		onHarvestBlock(event, CUSTOM_FLOWERS, (block) ->
		{
			incrementTask(event.getPlayer(), "gather_any_custom_flower_chaosmod");
		});

		onHarvestBlock(event, WHEAT, (block) ->
		{
			incrementTask(event.getPlayer(), "wheat_addict");
		});
	}

	private static void incrementTask(EntityPlayer player, String taskId) {
		JobEventUtils.incrementTask((EntityPlayerMP) player, "farmer", taskId);
	}
}