package net.chaos.chaosmod.market;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;

public class MarketDataHandler {
	public static MarketData get(World world) {
		MapStorage storage = world.isRemote ? world.getMapStorage() : world.getPerWorldStorage();
		MarketData instance = (MarketData) storage.getOrLoadData(MarketData.class, MarketData.DATA_MARKET);

		if (instance == null) {
			instance = new MarketData();
			storage.setData(MarketData.DATA_MARKET, instance);
		}
		return instance;
	}
	
	public void addOffer(World world, ItemStack stack, int price) {
	    if (!world.isRemote) {
	        MarketData data = MarketDataHandler.get(world);
	        data.addOffer(new MarketOffer(stack, price));
	    }
	}
}