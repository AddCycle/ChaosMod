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
	private final ModelRenderer shoulder;
	private final ModelRenderer fist;
	private final ModelRenderer right_arm;
	private final ModelRenderer shoulder2;
	private final ModelRenderer fist2;
	private final ModelRenderer legs;
	private final ModelRenderer leg1;
	private final ModelRenderer bottom;
	private final ModelRenderer top;
	private final ModelRenderer leg2;
	private final ModelRenderer top2;
	private final ModelRenderer bottom2;
	private final ModelRenderer body;
	private final ModelRenderer abs;
	private final ModelRenderer chest;
	private final ModelRenderer head;

	public MountainGiantModel() {
		textureWidth = 128;
		textureHeight = 128;

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(-13.0F, -14.0F, 0.0F);
		

		shoulder = new ModelRenderer(this);
		shoulder.setRotationPoint(0.0F, 1.0F, 0.0F);
		left_arm.addChild(shoulder);
		shoulder.cubeList.add(new ModelBox(shoulder, 0, 55, -3.0F, -2.0F, -3.0F, 6, 11, 6, 0.0F, false));

		fist = new ModelRenderer(this);
		fist.setRotationPoint(0.0F, 11.0F, 0.0F);
		left_arm.addChild(fist);
		fist.cubeList.add(new ModelBox(fist, 40, 35, -3.0F, -1.0F, -3.0F, 6, 15, 6, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(13.0F, -14.0F, 0.0F);
		

		shoulder2 = new ModelRenderer(this);
		shoulder2.setRotationPoint(0.0F, 1.0F, 0.0F);
		right_arm.addChild(shoulder2);
		shoulder2.cubeList.add(new ModelBox(shoulder2, 24, 56, -3.0F, -2.0F, -3.0F, 6, 11, 6, 0.0F, false));

		fist2 = new ModelRenderer(this);
		fist2.setRotationPoint(0.0F, 11.7213F, -0.9444F);
		right_arm.addChild(fist2);
		fist2.cubeList.add(new ModelBox(fist2, 52, 0, -3.0F, -1.7213F, -2.0556F, 6, 15, 6, 0.0F, false));

		legs = new ModelRenderer(this);
		legs.setRotationPoint(0.0F, 9.0F, 0.0F);
		

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(7.0F, -2.0F, 0.0F);
		legs.addChild(leg1);
		

		bottom = new ModelRenderer(this);
		bottom.setRotationPoint(-1.0F, 9.0F, 0.0F);
		leg1.addChild(bottom);
		bottom.cubeList.add(new ModelBox(bottom, 48, 56, -2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));

		top = new ModelRenderer(this);
		top.setRotationPoint(-1.0F, 1.0F, 0.0F);
		leg1.addChild(top);
		top.cubeList.add(new ModelBox(top, 60, 21, -2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(-6.0F, 0.0F, 0.0F);
		legs.addChild(leg2);
		

		top2 = new ModelRenderer(this);
		top2.setRotationPoint(0.0F, -1.0F, 0.0F);
		leg2.addChild(top2);
		top2.cubeList.add(new ModelBox(top2, 64, 33, -2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));

		bottom2 = new ModelRenderer(this);
		bottom2.setRotationPoint(0.0F, 7.0F, 0.0F);
		leg2.addChild(bottom2);
		bottom2.cubeList.add(new ModelBox(bottom2, 44, 21, -2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 8.0F, 0.0F);
		

		abs = new ModelRenderer(this);
		abs.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(abs);
		abs.cubeList.add(new ModelBox(abs, 0, 19, -8.0F, -10.0F, -3.0F, 16, 10, 6, 0.0F, false));

		chest = new ModelRenderer(this);
		chest.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(chest);
		chest.cubeList.add(new ModelBox(chest, 0, 0, -10.0F, -23.0F, -3.0F, 20, 13, 6, 0.0F, false));

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

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
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
		this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}
