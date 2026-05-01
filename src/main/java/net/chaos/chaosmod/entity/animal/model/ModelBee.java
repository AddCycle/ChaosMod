package net.chaos.chaosmod.entity.animal.model;
// Made with Blockbench 5.1.3

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelBee extends ModelBase {
	private final ModelRenderer torso;
	private final ModelRenderer left_antenna;
	private final ModelRenderer right_antenna;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_wing;
	private final ModelRenderer front_legs;
	private final ModelRenderer middle_legs;
	private final ModelRenderer back_legs;
	private final ModelRenderer stinger;

	public ModelBee() {
		textureWidth = 64;
		textureHeight = 64;

		torso = new ModelRenderer(this);
		torso.setRotationPoint(0.0F, 19.0F, 0.0F);
		torso.cubeList.add(new ModelBox(torso, 0, 0, -3.5F, -4.0F, -5.0F, 7, 7, 10, 0.0F, false));

		left_antenna = new ModelRenderer(this);
		left_antenna.setRotationPoint(0.0F, 17.0F, -5.0F);
		left_antenna.cubeList.add(new ModelBox(left_antenna, 2, 0, 1.5F, -2.0F, -3.0F, 1, 2, 3, 0.0F, false));

		right_antenna = new ModelRenderer(this);
		right_antenna.setRotationPoint(0.0F, 17.0F, -5.0F);
		right_antenna.cubeList.add(new ModelBox(right_antenna, 2, 3, -2.5F, -2.0F, -3.0F, 1, 2, 3, 0.0F, false));

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(1.5F, 15.0F, -3.0F);
		left_wing.cubeList.add(new ModelBox(left_wing, 0, 18, 0.0F, 0.0F, 0.0F, 9, 0, 6, 0.0F, true));

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(-1.5F, 15.0F, -3.0F);
		right_wing.cubeList.add(new ModelBox(right_wing, 0, 18, -9.0F, 0.0F, 0.0F, 9, 0, 6, 0.0F, false));

		front_legs = new ModelRenderer(this);
		front_legs.setRotationPoint(1.5F, 22.0F, -2.0F);
		front_legs.cubeList.add(new ModelBox(front_legs, 28, 1, -3.0F, 0.0F, 0.0F, 3, 2, 0, 0.0F, false));

		middle_legs = new ModelRenderer(this);
		middle_legs.setRotationPoint(1.5F, 22.0F, 0.0F);
		middle_legs.cubeList.add(new ModelBox(middle_legs, 27, 3, -4.0F, 0.0F, 0.0F, 5, 2, 0, 0.0F, false));

		back_legs = new ModelRenderer(this);
		back_legs.setRotationPoint(1.5F, 22.0F, 2.0F);
		back_legs.cubeList.add(new ModelBox(back_legs, 27, 5, -4.0F, 0.0F, 0.0F, 5, 2, 0, 0.0F, false));

		stinger = new ModelRenderer(this);
		stinger.setRotationPoint(0.0F, 19.0F, 0.0F);
		stinger.cubeList.add(new ModelBox(stinger, 24, 7, 0.0F, -1.0F, 5.0F, 0, 1, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		torso.render(f5);
		left_antenna.render(f5);
		right_antenna.render(f5);
		left_wing.render(f5);
		right_wing.render(f5);
		front_legs.render(f5);
		middle_legs.render(f5);
		back_legs.render(f5);
		stinger.render(f5);
	}
	
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		float flapSpeed = 1.8F;
		float flapAmount = 0.8F;
		this.right_wing.rotateAngleZ = MathHelper.cos(ageInTicks * flapSpeed) * flapAmount;
		this.left_wing.rotateAngleZ  = -MathHelper.cos(ageInTicks * flapSpeed) * flapAmount;
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}