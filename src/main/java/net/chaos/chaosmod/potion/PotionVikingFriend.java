package net.chaos.chaosmod.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

public class PotionVikingFriend extends Potion {

	public PotionVikingFriend(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setPotionName("effect.viking_friend");
		this.setRegistryName(new ResourceLocation(Reference.MODID, "viking_friend"));
        this.setIconIndex(0, 0);
	}
	
	@Override
	public boolean hasStatusIcon() {
		return true;
	}
	
	@Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/potions/icons.png"));
		return super.getStatusIconIndex();
    }

}
