package net.chaos.chaosmod.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BossAltarModel extends ModelBase {
	public ModelRenderer global;
	public ModelRenderer base_group;
	public ModelRenderer spikes;
	public ModelRenderer spike_1;
	public ModelRenderer spike_2;
	public ModelRenderer spike_3;
	public ModelRenderer spike_4;
	public ModelRenderer walls;

	public BossAltarModel() {
		textureWidth = 64;
		textureHeight = 64;

		global = new ModelRenderer(this);
		global.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		base_group = new ModelRenderer(this);
		base_group.setRotationPoint(0.0F, -2.0F, 0.0F);
		global.addChild(base_group);
		base_group.cubeList.add(new ModelBox(base_group, 0, 0, -8.0F, -2.0F, -8.0F, 16, 4, 16, 0.0F, false));

		spikes = new ModelRenderer(this);
		spikes.setRotationPoint(0.0F, 0.0F, 0.0F);
		global.addChild(spikes);
		

		spike_1 = new ModelRenderer(this);
		spike_1.setRotationPoint(5.5F, -12.0F, -5.5F);
		spikes.addChild(spike_1);
		spike_1.cubeList.add(new ModelBox(spike_1, 0, 20, -2.5F, 2.0F, -2.5F, 5, 6, 5, 0.0F, false));
		spike_1.cubeList.add(new ModelBox(spike_1, 0, 42, -1.5F, -3.0F, -1.5F, 3, 5, 3, 0.0F, false));
		spike_1.cubeList.add(new ModelBox(spike_1, 0, 50, -0.5F, -6.0F, -0.5F, 1, 3, 1, 0.0F, false));

		spike_2 = new ModelRenderer(this);
		spike_2.setRotationPoint(5.5F, -12.0F, 5.5F);
		spikes.addChild(spike_2);
		spike_2.cubeList.add(new ModelBox(spike_2, 20, 20, -2.5F, 2.0F, -2.5F, 5, 6, 5, 0.0F, false));
		spike_2.cubeList.add(new ModelBox(spike_2, 12, 42, -1.5F, -3.0F, -1.5F, 3, 5, 3, 0.0F, false));
		spike_2.cubeList.add(new ModelBox(spike_2, 4, 50, -0.5F, -6.0F, -0.5F, 1, 3, 1, 0.0F, false));

		spike_3 = new ModelRenderer(this);
		spike_3.setRotationPoint(-5.5F, -12.0F, 5.5F);
		spikes.addChild(spike_3);
		spike_3.cubeList.add(new ModelBox(spike_3, 0, 31, -2.5F, 2.0F, -2.5F, 5, 6, 5, 0.0F, false));
		spike_3.cubeList.add(new ModelBox(spike_3, 24, 42, -1.5F, -3.0F, -1.5F, 3, 5, 3, 0.0F, false));
		spike_3.cubeList.add(new ModelBox(spike_3, 8, 50, -0.5F, -6.0F, -0.5F, 1, 3, 1, 0.0F, false));

		spike_4 = new ModelRenderer(this);
		spike_4.setRotationPoint(-5.5F, -12.0F, -5.5F);
		spikes.addChild(spike_4);
		spike_4.cubeList.add(new ModelBox(spike_4, 20, 31, -2.5F, 2.0F, -2.5F, 5, 6, 5, 0.0F, false));
		spike_4.cubeList.add(new ModelBox(spike_4, 36, 42, -1.5F, -3.0F, -1.5F, 3, 5, 3, 0.0F, false));
		spike_4.cubeList.add(new ModelBox(spike_4, 12, 50, -0.5F, -6.0F, -0.5F, 1, 3, 1, 0.0F, false));

		walls = new ModelRenderer(this);
		walls.setRotationPoint(0.0F, -8.0F, 0.0F);
		global.addChild(walls);
		walls.cubeList.add(new ModelBox(walls, 40, 20, 7.0F, -1.0F, -3.0F, 1, 5, 6, 0.0F, false));
		walls.cubeList.add(new ModelBox(walls, 40, 31, -8.0F, -1.0F, -3.0F, 1, 5, 6, 0.0F, false));
		walls.cubeList.add(new ModelBox(walls, 48, 42, -3.0F, -1.0F, -8.0F, 6, 5, 1, 0.0F, false));
		walls.cubeList.add(new ModelBox(walls, 48, 48, -3.0F, -1.0F, 7.0F, 6, 5, 1, 0.0F, false));
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

	public void render(float f) {
		this.global.render(f);
	}
}