package net.chaos.chaosmod.gui;

import java.util.List;

import net.chaos.chaosmod.market.MarketClientCache;
import net.chaos.chaosmod.market.MarketOffer;
import net.minecraft.client.gui.GuiScreen;

public class GuiMarket extends GuiScreen {

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        List<MarketOffer> offers = MarketClientCache.getOffers();

        int y = 20;
        for (MarketOffer offer : offers) {
            String line = offer.getItem().getCount() + " " + offer.getItem().getDisplayName() + " - " + offer.getPrice() + " MC$";
            this.fontRenderer.drawString(line, 20, y, 0xFFFFFF);
            y += 12;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}