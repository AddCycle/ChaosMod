package net.chaos.chaosmod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * ModelIronGolem - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelForgeGuardian extends ModelBase {
    public ModelRenderer chest;
    public ModelRenderer belly;
    public ModelRenderer left_arm;
    public ModelRenderer right_arm;
    public ModelRenderer right_leg;
    public ModelRenderer head;
    public ModelRenderer nose;
    public ModelRenderer left_leg;

    public ModelForgeGuardian() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.chest = new ModelRenderer(this, 0, 40);
        this.chest.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.chest.addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, 0.0F);
        this.left_leg = new ModelRenderer(this, 60, 0);
        this.left_leg.mirror = true;
        this.left_leg.setRotationPoint(5.0F, 11.0F, 0.0F);
        this.left_leg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F);
        this.left_arm = new ModelRenderer(this, 60, 58);
        this.left_arm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.left_arm.addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F);
        this.right_leg = new ModelRenderer(this, 37, 0);
        this.right_leg.setRotationPoint(-4.0F, 11.0F, 0.0F);
        this.right_leg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, 0.0F);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -7.0F, -2.0F);
        this.nose.addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, 0.0F);
        this.belly = new ModelRenderer(this, 0, 70);
        this.belly.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.belly.addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, 0.5F);
        this.right_arm = new ModelRenderer(this, 60, 21);
        this.right_arm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.right_arm.addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -7.0F, -2.0F);
        this.head.addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.chest.render(f5);
        this.left_leg.render(f5);
        this.left_arm.render(f5);
        this.right_leg.render(f5);
        this.nose.render(f5);
        this.belly.render(f5);
        this.right_arm.render(f5);
        this.head.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
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
    	super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
}
