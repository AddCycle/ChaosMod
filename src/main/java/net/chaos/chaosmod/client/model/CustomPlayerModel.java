package net.chaos.chaosmod.client.model;

import net.chaos.chaosmod.client.renderer.player.ClientTauntManager;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomPlayerModel extends ModelPlayer {

	public CustomPlayerModel(float modelSize, boolean smallArmsIn) {
		super(modelSize, smallArmsIn);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		int taunt = ClientTauntManager.taunt;
		switch (taunt) {
			case 0:
				hiTaunt(ageInTicks);
				break;
			case 1:
				fortniteTaunt(ageInTicks);
				break;
			case 2:
				looserTaunt(ageInTicks);
				break;
			default:
				break;
		}
		
		ClientTauntManager.update();
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
	}

	private void hiTaunt(float ageInTicks) {
		this.bipedRightArm.rotateAngleX = (float) Math.toRadians(-150);
		this.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-(30 * Math.cos(ageInTicks)));
	}

	private void fortniteTaunt(float ageInTicks) {
		this.bipedRightArm.rotateAngleX = (float) Math.toRadians(30);
		this.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-(30 * Math.cos(ageInTicks)));
		this.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-30);
		this.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(-(30 * Math.cos(ageInTicks)));
	}

	private void looserTaunt(float ageInTicks) {
		this.bipedRightArm.rotateAngleX = (float) Math.toRadians(-150);
		this.bipedRightArm.rotateAngleZ = (float) Math.toRadians(30);
		this.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-30);
		this.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(30);
	}
}