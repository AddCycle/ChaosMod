package net.chaos.chaosmod.entity.boss.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.entity.Entity;

public class RevengeBlazeModel extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer stick1;
	private final ModelRenderer stick2;
	private final ModelRenderer stick3;
	private final ModelRenderer stick4;
	private final ModelRenderer stick5;
	private final ModelRenderer stick6;
	private final ModelRenderer stick7;
	private final ModelRenderer stick8;
	private final ModelRenderer stick9;
	private final ModelRenderer stick10;
	private final ModelRenderer stick11;
	private final ModelRenderer stick12;

	public RevengeBlazeModel() {
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F, false));

		stick1 = new ModelRenderer(this);
		stick1.setRotationPoint(-7.0F, -2.0F, -7.0F);
		stick1.cubeList.add(new ModelBox(stick1, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick2 = new ModelRenderer(this);
		stick2.setRotationPoint(7.0F, -2.0F, -7.0F);
		stick2.cubeList.add(new ModelBox(stick2, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick3 = new ModelRenderer(this);
		stick3.setRotationPoint(7.0F, -2.0F, 7.0F);
		stick3.cubeList.add(new ModelBox(stick3, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick4 = new ModelRenderer(this);
		stick4.setRotationPoint(-7.0F, -2.0F, 7.0F);
		stick4.cubeList.add(new ModelBox(stick4, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick5 = new ModelRenderer(this);
		stick5.setRotationPoint(-5.0F, 2.0F, -5.0F);
		stick5.cubeList.add(new ModelBox(stick5, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick6 = new ModelRenderer(this);
		stick6.setRotationPoint(5.0F, 2.0F, -5.0F);
		stick6.cubeList.add(new ModelBox(stick6, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick7 = new ModelRenderer(this);
		stick7.setRotationPoint(5.0F, 2.0F, 5.0F);
		stick7.cubeList.add(new ModelBox(stick7, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick8 = new ModelRenderer(this);
		stick8.setRotationPoint(-5.0F, 2.0F, 5.0F);
		stick8.cubeList.add(new ModelBox(stick8, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick9 = new ModelRenderer(this);
		stick9.setRotationPoint(-3.0F, 10.0F, -3.0F);
		stick9.cubeList.add(new ModelBox(stick9, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick10 = new ModelRenderer(this);
		stick10.setRotationPoint(3.0F, 10.0F, -3.0F);
		stick10.cubeList.add(new ModelBox(stick10, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick11 = new ModelRenderer(this);
		stick11.setRotationPoint(3.0F, 10.0F, 3.0F);
		stick11.cubeList.add(new ModelBox(stick11, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		stick12 = new ModelRenderer(this);
		stick12.setRotationPoint(-3.0F, 10.0F, 3.0F);
		stick12.cubeList.add(new ModelBox(stick12, 0, 16, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));
	}

	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
		head.render(scale);
		stick1.render(scale);
		stick2.render(scale);
		stick3.render(scale);
		stick4.render(scale);
		stick5.render(scale);
		stick6.render(scale);
		stick7.render(scale);
		stick8.render(scale);
		stick9.render(scale);
		stick10.render(scale);
		stick11.render(scale);
		stick12.render(scale);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}
