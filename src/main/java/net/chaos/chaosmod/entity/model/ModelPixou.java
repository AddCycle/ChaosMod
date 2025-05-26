// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports
package net.chaos.chaosmod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPixou extends ModelBase {
	private final ModelRenderer chest;
	private final ModelRenderer head;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;

	public ModelPixou() {
		textureWidth = 64;
		textureHeight = 64;

		chest = new ModelRenderer(this);
		chest.setRotationPoint(0.0F, 24.0F, 0.0F);
		chest.cubeList.add(new ModelBox(chest, 0, 0, -6.0F, -19.0F, 1.0F, 9, 11, 4, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 15, -4.0F, -24.0F, 1.0F, 5, 5, 4, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 26, 8, -3.0F, -22.0F, -1.0F, 3, 1, 2, 0.0F, false));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(-7.0F, 9.0F, 3.0F);
		right_arm.cubeList.add(new ModelBox(right_arm, 18, 25, -1.0F, -4.0F, -1.0F, 2, 8, 2, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(0.0F, 24.0F, 0.0F);
		left_arm.cubeList.add(new ModelBox(left_arm, 10, 24, 3.0F, -19.0F, 2.0F, 2, 8, 2, 0.0F, false));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(0.0F, 24.0F, 0.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 26, 0, -6.0F, -1.0F, -1.0F, 3, 1, 3, 0.0F, false));
		right_leg.cubeList.add(new ModelBox(right_leg, 18, 15, -6.0F, -8.0F, 2.0F, 3, 8, 2, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(6.0F, 24.0F, 0.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 26, 4, -6.0F, -1.0F, -1.0F, 3, 1, 3, 0.0F, false));
		left_leg.cubeList.add(new ModelBox(left_leg, 0, 24, -6.0F, -8.0F, 2.0F, 3, 8, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		chest.render(f5);
		head.render(f5);
		right_arm.render(f5);
		left_arm.render(f5);
		right_leg.render(f5);
		left_leg.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}