// Made with Blockbench 5.1.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package net.chaos.chaosmod.entity.animal.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelVulture extends ModelBase {
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;
	private final ModelRenderer chest;
	private final ModelRenderer cube_r1;
	private final ModelRenderer right_wing;
	private final ModelRenderer cube_r2;
	private final ModelRenderer tail;
	private final ModelRenderer cube_r3;
	private final ModelRenderer left_wing;
	private final ModelRenderer cube_r4;
	private final ModelRenderer neck;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer head;
	private final ModelRenderer nose;

	public ModelVulture() {
		textureWidth = 64;
		textureHeight = 64;

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(1.0F, 24.0F, 1.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 16, 32, -3.0F, -4.0F, -1.0F, 1, 4, 1, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(1.0F, 24.0F, 1.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 28, 31, 0.0F, -4.0F, -1.0F, 1, 4, 1, 0.0F, false));

		chest = new ModelRenderer(this);
		chest.setRotationPoint(3.0F, 21.0F, 0.0F);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-3.0F, -1.8828F, 0.8397F);
		chest.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1745F, 0.0F, 0.0F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -3.0F, -2.0F, -4.5F, 6, 4, 9, 0.0F, false));

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(-3.5F, 18.2757F, -2.3491F);
		

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 1.0F, 4.0F);
		right_wing.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.1745F, 0.0F, 0.0F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 13, -0.5F, -2.0F, -4.5F, 1, 4, 9, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 19.2313F, 5.1881F);
		

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 2.7687F, 1.8119F);
		tail.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.3491F, 0.0F, 0.0F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 28, 26, -2.0F, -3.0F, -1.0F, 4, 4, 1, 0.0F, false));

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(3.5F, 18.2757F, -2.3491F);
		

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 1.0F, 4.0F);
		left_wing.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.1745F, 0.0F, 0.0F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 20, 13, -0.5F, -2.0F, -4.5F, 1, 4, 9, 0.0F, false));

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, 21.5656F, -4.76F);
		

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, -2.2704F, 1.134F);
		neck.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.6109F, 0.0F, 0.0F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 16, 26, -1.0F, -1.0F, -2.0F, 2, 2, 4, 0.0F, false));

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, -3.5656F, -2.24F);
		neck.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.9599F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 30, 0, -1.0F, -2.0F, -1.0F, 2, 2, 3, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 17.5F, -6.0F);
		head.cubeList.add(new ModelBox(head, 0, 26, -2.0F, -4.5F, -2.0F, 4, 5, 4, 0.0F, false));

		nose = new ModelRenderer(this);
		nose.setRotationPoint(0.0F, -2.5F, -3.0F);
		head.addChild(nose);
		nose.cubeList.add(new ModelBox(nose, 30, 5, -1.0F, -2.0F, -1.0F, 2, 4, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		right_leg.render(f5);
		left_leg.render(f5);
		chest.render(f5);
		right_wing.render(f5);
		tail.render(f5);
		left_wing.render(f5);
		neck.render(f5);
		head.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		setRotationAngle(right_wing, 0, 0, 0);
		setRotationAngle(left_wing, 0, 0, 0);
//		this.right_wing.rotateAngleY = 0;
		this.right_wing.rotateAngleX = -(float) (Math.PI / 2); // right arm 90degrees
		this.left_wing.rotateAngleX = -(float) (Math.PI / 2); // left arm 90degrees
//		this.right_wing.rotateAngleZ = 0;
		float initAngleWings = (float) Math.PI / 2;
		float flapSpeed = 0.6662F;
		float flapAmount = 1.2F;

//		this.right_wing.rotateAngleZ = initAngleWings;
//		this.right_wing.rotateAngleZ += (float) (Math.cos(limbSwing * flapSpeed) * limbSwingAmount * flapAmount); // horizontal (for floating)
//
//		this.left_wing.rotateAngleZ = -initAngleWings;
//		this.left_wing.rotateAngleZ += (float) (Math.cos(limbSwing * flapSpeed) * limbSwingAmount * flapAmount); // horizontal (for floating)

		float flapSpeed2 = 0.2F;
		float flapAmount2 = 0.8F;
		this.right_wing.rotateAngleZ = initAngleWings + MathHelper.cos(ageInTicks * flapSpeed2) * flapAmount2;
		this.left_wing.rotateAngleZ  = -initAngleWings - MathHelper.cos(ageInTicks * flapSpeed2) * flapAmount2;
		
		float initAngleTail = 0.3491F;
		this.tail.rotateAngleX = initAngleTail + MathHelper.cos(headPitch) * 0.5F;

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		// hurt
		// flying
	}

    static enum State
    {
    	WALKING,
        FLYING,
        STANDING,
        SITTING;
//        PARTY;
    }

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}