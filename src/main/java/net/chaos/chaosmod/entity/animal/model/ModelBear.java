package net.chaos.chaosmod.entity.animal.model;

import net.chaos.chaosmod.entity.animal.EntityBear;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBear extends ModelPolarBear {

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {

		// ModelQuadruped code
		EntityBear entityBear = (EntityBear) entityIn;
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F;
		if (!entityBear.isSitting()) {
			this.body.rotateAngleX = ((float) Math.PI / 2F);
			this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
			this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
			this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		}

		// ModelPolarBear code
		float f = ageInTicks - (float) entityIn.ticksExisted;
		float f1 = ((EntityBear) entityIn).getStandingAnimationScale(f);
		f1 = f1 * f1;
		float f2 = 1.0F - f1;
		this.body.rotateAngleX = ((float) Math.PI / 2F) - f1 * (float) Math.PI * 0.35F;
		if (!entityBear.isSitting()) {
			this.body.rotationPointY = 9.0F * f2 + 11.0F * f1;
			this.leg3.rotationPointY = 14.0F * f2 + -6.0F * f1;
			this.leg3.rotationPointZ = -8.0F * f2 + -4.0F * f1;
			this.leg3.rotateAngleX -= f1 * (float) Math.PI * 0.45F;
			this.leg4.rotationPointY = this.leg3.rotationPointY;
			this.leg4.rotationPointZ = this.leg3.rotationPointZ;
			this.leg4.rotateAngleX -= f1 * (float) Math.PI * 0.45F;
		}
		this.head.rotationPointY = 10.0F * f2 + -12.0F * f1;
		this.head.rotationPointZ = -16.0F * f2 + -3.0F * f1;
		this.head.rotateAngleX += f1 * (float) Math.PI * 0.15F;
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		EntityBear entitybear = (EntityBear) entity;

		if (entitybear.isSitting()) {

			int pose = entitybear.getSitPose();
			if (pose == 0) {
				layDownLegsStretching();
			} else if (pose == 1) {
				sitLegsCrossedLying();
			} else if (pose == 2) {
				sitOnGroundStraight();
			}
		} else {
			resetBody();
			resetHead();
			resetLegs();
		}
	}

	private void sitOnGroundStraight() {
		float offsetY = 0.5F;
		this.body.offsetY = offsetY;
		this.head.offsetY = offsetY;

		this.leg1.rotateAngleZ = (float) Math.PI / 2;
		this.leg1.rotateAngleX = (float) Math.PI / 3;
		this.leg1.offsetY = offsetY;

		this.leg2.rotateAngleZ = -(float) Math.PI / 2;
		this.leg2.rotateAngleX = (float) Math.PI / 3;
		this.leg2.offsetY = offsetY;

		this.leg3.rotateAngleZ = (float) Math.PI / 2;
		this.leg3.rotateAngleX = -(float) Math.PI / 3;
		this.leg3.offsetY = offsetY;

		this.leg4.rotateAngleZ = -(float) Math.PI / 2;
		this.leg4.rotateAngleX = -(float) Math.PI / 3;
		this.leg4.offsetY = offsetY;
	}

	private void layDownLegsStretching() {
		float offsetY = 0.5F;
		this.body.offsetY = offsetY;
		this.head.offsetY = offsetY;

		this.leg1.rotateAngleZ = (float) Math.PI / 2;
		this.leg1.rotateAngleX = 0;
		this.leg1.offsetY = offsetY;

		this.leg2.rotateAngleZ = -(float) Math.PI / 2;
		this.leg2.rotateAngleX = 0;
		this.leg2.offsetY = offsetY;

		this.leg3.rotateAngleZ = (float) Math.PI / 2;
		this.leg3.rotateAngleX = 0;
		this.leg3.offsetY = offsetY;

		this.leg4.rotateAngleZ = -(float) Math.PI / 2;
		this.leg4.rotateAngleX = 0;
		this.leg4.offsetY = offsetY;
	}

	private void sitLegsCrossedLying() {
		float offsetY = 0.5F;
		this.body.offsetY = offsetY;
		this.head.offsetY = offsetY;

		this.leg1.rotateAngleZ = (float) Math.PI / 2;
		this.leg1.rotateAngleX = (float) Math.PI / 4;
		this.leg1.offsetY = offsetY;

		this.leg2.rotateAngleZ = -(float) Math.PI / 2;
		this.leg2.rotateAngleX = (float) Math.PI / 4;
		this.leg2.offsetY = offsetY;

		this.leg3.rotateAngleZ = (float) Math.PI / 2;
		this.leg3.rotateAngleX = -(float) Math.PI / 4;
		this.leg3.offsetY = offsetY;

		this.leg4.rotateAngleZ = -(float) Math.PI / 2;
		this.leg4.rotateAngleX = -(float) Math.PI / 4;
		this.leg4.offsetY = offsetY;
	}

	private void resetHead() {
		resetOffset(head);
	}

	private void resetBody() {
		resetRotationAndOffset(body);
		this.body.setRotationPoint(-2.0F, 9.0F, 12.0F);
	}

	private void resetLegs() {
		resetRotationAndOffset(leg1);
		resetRotationAndOffset(leg2);
		resetRotationAndOffset(leg3);
		resetRotationAndOffset(leg4);
	}

	private void resetRotationAndOffset(ModelRenderer modelRenderer) {
		resetRotation(modelRenderer);
		resetOffset(modelRenderer);
	}

	private void resetRotation(ModelRenderer modelRenderer) {
		modelRenderer.rotateAngleX = 0.0f;
		modelRenderer.rotateAngleY = 0.0f;
		modelRenderer.rotateAngleZ = 0.0f;
	}

	private void resetOffset(ModelRenderer modelRenderer) {
		modelRenderer.offsetX = 0.0f;
		modelRenderer.offsetY = 0.0f;
		modelRenderer.offsetZ = 0.0f;
	}

    public void setVisible(boolean visible)
    {
        this.head.showModel = visible;
//        this.bipedHeadwear.showModel = visible;
        this.body.showModel = visible;
        this.leg1.showModel = visible;
        this.leg2.showModel = visible;
        this.leg3.showModel = visible;
        this.leg4.showModel = visible;
    }
}