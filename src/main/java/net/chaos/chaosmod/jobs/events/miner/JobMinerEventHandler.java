package net.chaos.chaosmod.jobs.events.miner;

import static net.chaos.chaosmod.jobs.events.JobEventUtils.onHarvestBlock;

import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

// TODO : do the rest of miner jobs
@EventBusSubscriber(modid = Reference.MODID)
public class JobMinerEventHandler {
	private static final ResourceLocation COBBLESTONE = new ResourceLocation("cobblestone");
	private static final ResourceLocation IRON_ORE = new ResourceLocation("iron_ore");
	private static final ResourceLocation SILVER_ORE = new ResourceLocation(Reference.MATHSMOD, "silver_ore");
	private static final ResourceLocation ALLEMANITE_ORE = new ResourceLocation(Reference.MODID, "allemanite_ore");
	private static final ResourceLocation KURAYUM_ORE = new ResourceLocation(Reference.MATHSMOD, "kurayum_ore");

	@SubscribeEvent
	public static void onBlockMined(BlockEvent.BreakEvent event) {
		if (event.getWorld().isRemote) return;
		
		onHarvestBlock(event, COBBLESTONE, block -> {
			incrementTask(event.getPlayer(), "mine_cobblestone");
		});

		onHarvestBlock(event, IRON_ORE, block -> {
			incrementTask(event.getPlayer(), "mine_iron");
		});

		onHarvestBlock(event, SILVER_ORE, block -> {
			incrementTask(event.getPlayer(), "mine_silver");
		});

		onHarvestBlock(event, ALLEMANITE_ORE, block -> {
			incrementTask(event.getPlayer(), "mine_allemanite");
		});

		onHarvestBlock(event, KURAYUM_ORE, block -> {
			incrementTask(event.getPlayer(), "mine_kurayum");
		});
	}

	private static void incrementTask(EntityPlayer player, String taskId) {
		JobEventUtils.incrementTask((EntityPlayerMP) player, "miner", taskId);
	}
}