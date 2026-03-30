package net.chaos.chaosmod.entity.model;

import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class SwordOfWrathModel extends ModelBase {
	private final ModelRenderer sword;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;

	public SwordOfWrathModel() {
		textureWidth = 64;
		textureHeight = 64;

		sword = new ModelRenderer(this);
		sword.setRotationPoint(0.5F, 0.0F, 0.5F);
		sword.cubeList.add(new ModelBox(sword, 0, 0, -2.0F, 1.0F, 0.0F, 4, 17, 1, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 11, 12, -2.0F, 18.0F, 0.0F, 4, 1, 1, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 18, 15, -1.0F, 19.0F, 0.0F, 2, 1, 1, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 11, 0, -5.0F, -1.0F, 0.0F, 10, 2, 1, 0.0F, false));
		sword.cubeList.add(new ModelBox(sword, 11, 4, -1.0F, -7.0F, 0.0F, 2, 6, 1, 0.0F, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(5.0F, 0.0F, 0.5F);
		sword.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.7854F);

		cube_r1.cubeList.add(new ModelBox(cube_r1, 18, 8, -1.0F, -1.0F, -0.5F, 2, 2, 1, 0.0F, false));

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-5.0F, 0.0F, 0.5F);
		sword.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -0.7854F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 18, 4, -1.0F, -1.0F, -0.5F, 2, 2, 1, 0.0F, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -7.0F, 0.5F);
		sword.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.7854F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 11, 15, -1.0F, -1.0F, -0.5F, 2, 2, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		sword.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
//		this.sword.rotateAngleY = (float) (Math.PI / 2);
//		this.sword.rotateAngleZ = 0;
//		this.sword.rotateAngleY += (float) (Math.PI / 24);
//		this.sword.rotateAngleZ += (float) (Math.PI / 24);
//		this.sword.rotateAngleX += (float) (Math.PI / 24);
//		this.sword.rotateAngleX = 0;
//		this.sword.rotateAngleX = (float) (Math.PI / 6);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		if (!(entity instanceof EntitySwordOfWrath))
			return;

		EntitySwordOfWrath sword = (EntitySwordOfWrath) entity;
		
		EntityLivingBase target = sword.getAttackTarget();

		// face the entity first
		if (target != null) {
		    double dx = target.posX - sword.posX;
		    double dz = target.posZ - sword.posZ;

		    float yaw = (float) Math.atan2(dz, dx);
		    this.sword.rotateAngleY = yaw - (float) Math.PI / 2;
//		    this.sword.rotateAngleY = (float) (Math.atan2(dx, dz) + Math.PI / 2);
		}

		if (sword.getAttackTicks() > 0) {
			float progress = (10 - sword.getAttackTicks()) / 10.0f;

			// fast slash
			this.sword.rotateAngleZ = (float) Math.sin(progress * Math.PI) * 2.0f;
		} else {
			this.sword.rotateAngleX = 0;
//			this.sword.rotateAngleY = 0;
			this.sword.rotateAngleZ = 0;
		}
	}
}