// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package net.chaos.chaosmod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ChaosSageModel extends ModelBase {
	private final ModelRenderer global;
	private final ModelRenderer body;
	private final ModelRenderer legs;
	private final ModelRenderer right_leg_r1;
	private final ModelRenderer left_leg_r1;
	private final ModelRenderer head;
	private final ModelRenderer arms;
	private final ModelRenderer right_arm_r1;
	private final ModelRenderer left_arm_r1;

	public ChaosSageModel() {
		textureWidth = 64;
		textureHeight = 64;

		global = new ModelRenderer(this);
		global.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		global.addChild(body);
		body.cubeList.add(new ModelBox(body, 0, 0, -6.0F, -20.0F, -2.0F, 12, 10, 4, 0.0F, false));

		legs = new ModelRenderer(this);
		legs.setRotationPoint(0.0F, 0.0F, 0.0F);
		global.addChild(legs);
		

		right_leg_r1 = new ModelRenderer(this);
		right_leg_r1.setRotationPoint(4.0F, -12.0F, 1.0F);
		legs.addChild(right_leg_r1);
		setRotationAngle(right_leg_r1, 1.5222F, -0.7186F, -0.0038F);
		right_leg_r1.cubeList.add(new ModelBox(right_leg_r1, 0, 23, -1.0F, 0.5736F, -1.8192F, 2, 10, 2, 0.0F, false));

		left_leg_r1 = new ModelRenderer(this);
		left_leg_r1.setRotationPoint(-4.0F, -12.0F, 1.0F);
		legs.addChild(left_leg_r1);
		setRotationAngle(left_leg_r1, 1.5268F, 0.5894F, -0.0603F);
		left_leg_r1.cubeList.add(new ModelBox(left_leg_r1, 18, 14, -1.0F, 0.5736F, -1.8192F, 2, 10, 2, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		global.addChild(head);
		head.cubeList.add(new ModelBox(head, 0, 14, -3.0F, -26.0F, -2.0F, 6, 6, 3, 0.0F, false));

		arms = new ModelRenderer(this);
		arms.setRotationPoint(0.0F, 0.0F, 0.0F);
		global.addChild(arms);
		

		right_arm_r1 = new ModelRenderer(this);
		right_arm_r1.setRotationPoint(3.0F, -19.0F, 0.0F);
		arms.addChild(right_arm_r1);
		setRotationAngle(right_arm_r1, 1.6738F, -0.1424F, -0.1644F);
		right_arm_r1.cubeList.add(new ModelBox(right_arm_r1, 26, 14, -1.0F, 0.5736F, -1.8192F, 2, 10, 2, 0.0F, false));

		left_arm_r1 = new ModelRenderer(this);
		left_arm_r1.setRotationPoint(-4.0F, -19.0F, 0.0F);
		arms.addChild(left_arm_r1);
		setRotationAngle(left_arm_r1, 1.6726F, 0.1685F, 0.0441F);
		left_arm_r1.cubeList.add(new ModelBox(left_arm_r1, 8, 23, -1.0F, 0.5736F, -1.8192F, 2, 10, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		global.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		this.global.offsetY = 0.10f * MathHelper.sin((float) 0.2 * ageInTicks);
		// super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
	}
}