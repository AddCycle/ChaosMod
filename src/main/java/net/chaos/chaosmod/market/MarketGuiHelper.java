package net.chaos.chaosmod.market;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.market.PacketSyncMarket;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import util.Reference;

public class MarketGuiHelper {

	public static void openMarketGui(EntityPlayerMP player, World world) {
	    MarketData data = MarketDataHandler.get(world);

	    PacketManager.network.sendTo(new PacketSyncMarket(data), player);

	    player.openGui(Main.instance, Reference.GUI_MARKET_ID, world,
	                   (int) player.posX, (int) player.posY, (int) player.posZ);
	}
}
