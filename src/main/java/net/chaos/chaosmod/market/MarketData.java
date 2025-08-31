package net.chaos.chaosmod.market;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.Main;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import util.Reference;

public class MarketData extends WorldSavedData {
	public static final String DATA_MARKET = Reference.PREFIX + "market_items";
	
	private final List<MarketOffer> offers = new ArrayList<>();
	
	public MarketData() {
		super(DATA_MARKET);
	}

	public MarketData(String name) {
		super(name);
	}
	
	public List<MarketOffer> getOffers() {
		return offers;
	}
	
	public void addOffer(MarketOffer offer) {
		this.offers.add(offer);
		markDirty();
	}
	
	public void clearOffers() {
		this.offers.clear();
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		offers.clear();
		NBTTagList list = nbt.getTagList("Offers", 10); // 10 = NBTTagCompound
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound offerTag = list.getCompoundTagAt(i);
            offers.add(MarketOffer.fromNBT(offerTag));
        }
        Main.getLogger().info("[MarketData] Loaded " + offers.size() + " offers from disk");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for (MarketOffer offer : offers) {
            list.appendTag(offer.toNBT());
        }

		compound.setTag("Offers", list);

        Main.getLogger().info("[MarketData] Saved " + offers.size() + " offers from disk");
        return compound;
	}
}
