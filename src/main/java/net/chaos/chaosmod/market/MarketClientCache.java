package net.chaos.chaosmod.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarketClientCache {
	private static final List<MarketOffer> offers = new ArrayList<>();

    public static void updateOffers(List<MarketOffer> newOffers) {
    	clear();
        offers.addAll(newOffers);
    }

    public static List<MarketOffer> getOffers() {
        return Collections.unmodifiableList(offers);
    }

    public static void clear() {
        offers.clear();
    }
}