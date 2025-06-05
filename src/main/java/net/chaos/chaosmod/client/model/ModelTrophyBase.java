package net.chaos.chaosmod.client.model;
// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTrophyBase extends ModelBase {
	private final ModelRenderer bases;
	private final ModelRenderer center;

	public ModelTrophyBase() {
		textureWidth = 64;
		textureHeight = 64;

		bases = new ModelRenderer(this);
		bases.setRotationPoint(0.0F, 24.0F, 0.0F);
		bases.cubeList.add(new ModelBox(bases, 0, 0, -5.0F, -2.0F, -5.0F, 10, 2, 10, 0.0F, false));
		bases.cubeList.add(new ModelBox(bases, 0, 32, -5.0F, -16.0F, -5.0F, 10, 2, 10, 0.0F, false));

		center = new ModelRenderer(this);
		center.setRotationPoint(0.0F, 22.0F, 0.0F);
		center.cubeList.add(new ModelBox(center, 0, 12, -4.0F, -12.0F, -4.0F, 8, 12, 8, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		bases.render(f5);
		center.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void render(float f) {
		bases.render(f);
		center.render(f);
	}
}