package net.chaos.chaosmod.market;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.market.PacketSyncMarket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import util.Reference;

public class MarketGuiHelper {

	public static void openMarketGui(EntityPlayerMP player, World world) {
	    MarketData data = MarketDataHandler.get(world);
	    if (data == null) {
	        player.sendMessage(new TextComponentString("market data is null"));
	    } else {
	        player.sendMessage(new TextComponentString("market data isn't null"));
	    }

	    // Send sync packet
	    PacketManager.network.sendTo(new PacketSyncMarket(data), player);

	    // Open GUI as usual
	    // Main.getLogger().info("opening gui...");
	    player.openGui(Main.instance, Reference.GUI_MARKET_ID, world,
	                   (int) player.posX, (int) player.posY, (int) player.posZ);
	}
}
