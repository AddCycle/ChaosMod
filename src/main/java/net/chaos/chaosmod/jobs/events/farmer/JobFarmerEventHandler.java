package net.chaos.chaosmod.jobs.events.farmer;

import java.util.function.Consumer;

import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobFarmerEventHandler {
	private static final ResourceLocation DIAMONA = new ResourceLocation(Reference.MATHSMOD, "diamona");
	private static final ResourceLocation HELL_FLOWER = new ResourceLocation(Reference.MATHSMOD, "hell_flower");
	private static final ResourceLocation OXONIUM_CARROTS = new ResourceLocation(Reference.MODID, "oxonium_carrots");
	
	@SubscribeEvent
	public static void onHarvestBlockHandler(BlockEvent.BreakEvent event) {
		if (event.getWorld().isRemote) return;
		
		onHarvestBlock(event, OXONIUM_CARROTS, block -> {
			incrementTask(event.getPlayer(), "farm_oxonium_carrots");
		});
		
		onHarvestBlock(event, DIAMONA, (block) -> {
			incrementTask(event.getPlayer(), "gather_diamona");
		});

		onHarvestBlock(event, HELL_FLOWER, (block) -> {
			incrementTask(event.getPlayer(), "gather_hell_flower");
		});
	}
	
	private static void onHarvestBlock(BlockEvent.BreakEvent event, ResourceLocation blockId, Consumer<Block> callback) {
		Block block = ForgeRegistries.BLOCKS.getValue(blockId);
		if (block == null || block != event.getState().getBlock()) return;
		
		callback.accept(block);
	}
	
	private static void incrementTask(EntityPlayer player, String taskId) {
		JobEventUtils.incrementTask((EntityPlayerMP) player, "farmer", taskId);
	}
}