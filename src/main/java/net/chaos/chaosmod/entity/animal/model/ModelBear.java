package net.chaos.chaosmod.entity.animal.model;

import net.chaos.chaosmod.entity.animal.EntityBear;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBear extends ModelPolarBear {

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {

		// ModelQuadruped code
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        // ModelPolarBear code
        float f = ageInTicks - (float)entityIn.ticksExisted;
        float f1 = ((EntityBear)entityIn).getStandingAnimationScale(f);
        f1 = f1 * f1;
        float f2 = 1.0F - f1;
        this.body.rotateAngleX = ((float)Math.PI / 2F) - f1 * (float)Math.PI * 0.35F;
        this.body.rotationPointY = 9.0F * f2 + 11.0F * f1;
        this.leg3.rotationPointY = 14.0F * f2 + -6.0F * f1;
        this.leg3.rotationPointZ = -8.0F * f2 + -4.0F * f1;
        this.leg3.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
        this.leg4.rotationPointY = this.leg3.rotationPointY;
        this.leg4.rotationPointZ = this.leg3.rotationPointZ;
        this.leg4.rotateAngleX -= f1 * (float)Math.PI * 0.45F;
        this.head.rotationPointY = 10.0F * f2 + -12.0F * f1;
        this.head.rotationPointZ = -16.0F * f2 + -3.0F * f1;
        this.head.rotateAngleX += f1 * (float)Math.PI * 0.15F;
	}
}