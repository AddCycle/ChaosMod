// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package net.chaos.chaosmod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGiants extends ModelBase {
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;

	public ModelGiants() {
		textureWidth = 32;
		textureHeight = 32;

		left_leg = new ModelRenderer(this);
		// left_leg.setRotationPoint(0.0F, 24.0F, 0.0F);
		left_leg.setRotationPoint(0.0F, 24.0F, 0.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 20, 0, 2.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F, false));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(-3.0F, 24.0F, 0.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 8, 20, -1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 0, -4.0F, -16.0F, -1.0F, 8, 10, 2, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 18.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 12, -2.0F, -14.0F, -2.0F, 4, 4, 4, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(0.0F, 7.0F, 0.0F);
		right_arm.cubeList.add(new ModelBox(right_arm, 16, 12, -6.0F, 1.0F, -1.0F, 2, 7, 2, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(5.0F, 10.0F, 0.0F);
		left_arm.cubeList.add(new ModelBox(left_arm, 0, 20, -1.0F, -2.0F, -1.0F, 2, 7, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		left_leg.render(f5);
		right_leg.render(f5);
		body.render(f5);
		head.render(f5);
		right_arm.render(f5);
		left_arm.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		// Head rotation
	    this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	    this.head.rotateAngleX = headPitch * 0.017453292F;

	    // Limb swing
	    this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
	    this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;

	    this.right_arm.rotateAngleZ = 0.0F;
	    this.left_arm.rotateAngleZ = 0.0F;

	    // Subtle arm sway (adds realism)
	    this.right_arm.rotateAngleY = 0.1F * MathHelper.sin(ageInTicks * 0.1F);
	    this.left_arm.rotateAngleY = -0.1F * MathHelper.sin(ageInTicks * 0.1F);

	    // Legs
	    this.right_leg.rotateAngleX = -MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	    this.left_leg.rotateAngleX = -MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

	    this.right_leg.rotateAngleY = 0.0F;
	    this.left_leg.rotateAngleY = 0.0F;

	    this.right_leg.rotateAngleZ = 0.0F;
	    this.left_leg.rotateAngleZ = 0.0F;
	}
}