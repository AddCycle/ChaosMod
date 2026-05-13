// Made with Blockbench 5.1.4
package net.chaos.chaosmod.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelWings extends ModelBiped {
	private final ModelRenderer wings;
	private final ModelRenderer wing_left;
	private final ModelRenderer wing_right;

	public ModelWings() {
		textureWidth = 64;
		textureHeight = 64;

		wings = new ModelRenderer(this);
		wings.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		wing_left = new ModelRenderer(this);
		wing_left.setRotationPoint(3.0F, -19.0F, 2.0F);
		wings.addChild(wing_left);
		wing_left.cubeList.add(new ModelBox(wing_left, 0, 19, 0.0F, -5.0F, 0.0F, 0, 10, 13, 0.0F, false));

		wing_right = new ModelRenderer(this);
		wing_right.setRotationPoint(-3.0F, -19.0F, 2.0F);
		wings.addChild(wing_right);
		wing_right.cubeList.add(new ModelBox(wing_right, 0, 19, 0.0F, -5.0F, 0.0F, 0, 10, 13, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		wings.render(f5);
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
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		float initAngleRight = (float) -(Math.PI/3);
		float initAngleLeft = (float) (Math.PI/3);
		float amplitude = (float) (Math.PI / 8);
		float speed = 0.5f;
		float ageInTicks = entitylivingbaseIn.ticksExisted;
//		Main.getLogger().info("ageInTicks: {}", ageInTicks);
		wing_right.rotateAngleY = initAngleRight + MathHelper.cos(ageInTicks * speed) * amplitude;
		wing_left.rotateAngleY = initAngleLeft - MathHelper.cos(ageInTicks * speed) * amplitude;
	}
}