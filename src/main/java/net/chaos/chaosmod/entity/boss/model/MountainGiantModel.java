// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.14 with MCP mappings
// Paste this class into your mod and generate all required imports
package net.chaos.chaosmod.entity.boss.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MountainGiantModel extends ModelBase {
	private final ModelRenderer left_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer legs;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer body;
	private final ModelRenderer head;

	public MountainGiantModel() {
		textureWidth = 128;
		textureHeight = 128;

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(-13.0F, -14.0F, 0.0F);
		left_arm.cubeList.add(new ModelBox(left_arm, 0, 55, -3.0F, -1.0F, -3.0F, 6, 11, 6, 0.0F, false));
		left_arm.cubeList.add(new ModelBox(left_arm, 40, 35, -3.0F, 10.0F, -3.0F, 6, 15, 6, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(13.0F, -14.0F, 0.0F);
		right_arm.cubeList.add(new ModelBox(right_arm, 24, 56, -3.0F, -1.0F, -3.0F, 6, 11, 6, 0.0F, false));
		right_arm.cubeList.add(new ModelBox(right_arm, 52, 0, -3.0F, 10.0F, -3.0F, 6, 15, 6, 0.0F, false));

		legs = new ModelRenderer(this);
		legs.setRotationPoint(0.0F, 9.0F, 0.0F);
		

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(7.0F, -2.0F, 0.0F);
		legs.addChild(leg1);
		leg1.cubeList.add(new ModelBox(leg1, 48, 56, -3.0F, 9.0F, -2.0F, 4, 8, 4, 0.0F, false));
		leg1.cubeList.add(new ModelBox(leg1, 60, 21, -3.0F, 1.0F, -2.0F, 4, 8, 4, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-6.0F, 0.0F, 0.0F);
		legs.addChild(leg2);
		leg2.cubeList.add(new ModelBox(leg2, 64, 33, -2.0F, -1.0F, -2.0F, 4, 8, 4, 0.0F, false));
		leg2.cubeList.add(new ModelBox(leg2, 44, 21, -2.0F, 7.0F, -2.0F, 4, 8, 4, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 8.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 19, -8.0F, -10.0F, -3.0F, 16, 10, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -10.0F, -23.0F, -3.0F, 20, 13, 6, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -20.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 35, -5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		left_arm.render(f5);
		right_arm.render(f5);
		legs.render(f5);
		body.render(f5);
		head.render(f5);
	}
	
	public void resetAngles() {
		head.rotateAngleY = 0;
		left_arm.rotateAngleY = 0;
		body.rotateAngleY = 0;
		leg1.rotateAngleY = 0;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		// resetAngles();
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.head.rotateAngleY = netHeadYaw * 0.017453292F; // Convert degrees to radians
		this.head.rotateAngleX = headPitch * 0.017453292F;
		this.body.rotateAngleZ = 0.05F * MathHelper.sin(ageInTicks * 0.1F);
		/*head.rotateAngleY = (float) (ageInTicks % (2 * Math.PI));
		left_arm.rotateAngleY = (float) (ageInTicks % (2 * Math.PI));
		body.rotateAngleY = (float) (ageInTicks % (2 * Math.PI));
		leg1.rotateAngleY = (float) (ageInTicks % (2 * Math.PI));*/
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}
