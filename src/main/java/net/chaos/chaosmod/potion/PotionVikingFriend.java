package net.chaos.chaosmod.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class PotionVikingFriend extends Potion {

	public PotionVikingFriend(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setPotionName("effect.viking_friend");
		this.setRegistryName(new ResourceLocation(Reference.MODID, "viking_friend"));
        this.setIconIndex(0, 0);
	}
	
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/gui/potion_icons.png"));
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 144, 198, 18, 18);
	}
	
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/gui/potion_icons.png"));
        mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, 144, 198, 18, 18);
	}
	
	@Override
	public boolean hasStatusIcon() {
		return false;
	}

}
