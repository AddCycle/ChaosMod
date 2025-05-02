package net.chaos.chaosmod.world.events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.chaos.chaosmod.Main;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.broadcast.ClientMessageHandler;
import util.broadcast.MessageDisplayText;

@EventBusSubscriber
public class PlayerTickBiomeEvent {
	private static final Map<UUID, Biome> lastBiomes = new HashMap<>();
	private static final List<String> customBiomeNames = Arrays.asList("Giant Mountains");

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
	    if (event.phase == TickEvent.Phase.END || event.player.world.isRemote) return;

	    EntityPlayer player = event.player;
	    BlockPos pos = player.getPosition();
	    Biome currentBiome = player.world.getBiome(pos);
	    UUID playerId = player.getUniqueID();

	    Biome lastBiome = lastBiomes.get(playerId);
	    if (lastBiome != currentBiome) {
	    	// if (customBiomeNames.contains(currentBiome.getBiomeName())) {
	    		player.sendMessage(new TextComponentString("Now entering: " + currentBiome.getRegistryName()));
	    		String message = TextFormatting.RESET + "Now entering: " + TextFormatting.RED + "" + TextFormatting.BOLD + currentBiome.getRegistryName().toString();
	    		if (player instanceof EntityPlayerMP) {
	    			Main.network.sendTo(new MessageDisplayText(message), (EntityPlayerMP) player);
	    		} else if (player instanceof EntityPlayerSP) {
	    			// Singleplayer client
	    			ClientMessageHandler.displayMessage(message);
	    		}
	    	// }
	        lastBiomes.put(playerId, currentBiome);
	    }
	}

}
