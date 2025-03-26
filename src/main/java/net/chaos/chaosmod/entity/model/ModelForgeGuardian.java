package net.chaos.chaosmod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;

/**
 * ModelIronGolem - Either Mojang or a mod author
 * Created using Tabula 7.1.0
 */
public class ModelForgeGuardian extends ModelBase {
	private float to_turn;
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
    	resetRotation();
    	// rotateRightAnimation(45f);
    	/*System.out.println("TICKS AGE : " + (int) ageInTicks);
    	System.out.println("RIGHT X : " + this.right_arm.rotateAngleX);
    	System.out.println("LEFT X : " + this.left_arm.rotateAngleX);*/
    	// super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    }
    
    @Override
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
    		float partialTickTime) {
    	super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
    
    public void resetRotation() {
    	this.head.rotateAngleX = 0f;
    	this.head.rotateAngleY = 0f;
    	this.head.rotateAngleZ = 0f;
    	this.nose.rotateAngleX = 0f;
    	this.nose.rotateAngleY = 0f;
    	this.nose.rotateAngleZ = 0f;
    	this.chest.rotateAngleX = 0f;
    	this.chest.rotateAngleY = 0f;
    	this.chest.rotateAngleZ = 0f;
    	this.belly.rotateAngleX = 0f;
    	this.belly.rotateAngleY = 0f;
    	this.belly.rotateAngleZ = 0f;
    	this.left_arm.rotateAngleX = 0f;
    	this.left_arm.rotateAngleY = 0f;
    	this.left_arm.rotateAngleZ = 0f;
    	this.right_arm.rotateAngleX = 0f;
    	this.right_arm.rotateAngleY = 0f;
    	this.right_arm.rotateAngleZ = 0f;
    	this.left_leg.rotateAngleX = 0f;
    	this.left_leg.rotateAngleY = 0f;
    	this.left_leg.rotateAngleZ = 0f;
    	this.right_leg.rotateAngleX = 0f;
    	this.right_leg.rotateAngleY = 0f;
    	this.right_leg.rotateAngleZ = 0f;
    }

    public void rotateRightAnimation(float angle) {
    	this.head.rotateAngleX = 0f;
    	this.head.rotateAngleY -= angle;
    	this.head.rotateAngleZ = 0f;
    	this.nose.rotateAngleX = 0f;
    	this.nose.rotateAngleY -= angle;
    	this.nose.rotateAngleZ = 0f;
    	this.chest.rotateAngleX = 0f;
    	this.chest.rotateAngleY -= angle;
    	this.chest.rotateAngleZ = 0f;
    	this.belly.rotateAngleX = 0f;
    	this.belly.rotateAngleY -= angle;
    	this.belly.rotateAngleZ = 0f;
    	this.left_arm.rotateAngleX = 0f;
    	this.left_arm.rotateAngleY -= angle;
    	this.left_arm.rotateAngleZ = 0f;
    	this.right_arm.rotateAngleX = 0f;
    	this.right_arm.rotateAngleY -= angle;
    	this.right_arm.rotateAngleZ = 0f;
    	this.left_leg.rotateAngleX = 0f;
    	this.left_leg.rotateAngleY -= angle;
    	this.left_leg.rotateAngleZ = 0f;
    	this.right_leg.rotateAngleX = 0f;
    	this.right_leg.rotateAngleY -= angle;
    	this.right_leg.rotateAngleZ = 0f;
    }
    
    public float getTurn() {
    	return this.to_turn;
    }

    public void setTurn(float angle) {
    	this.to_turn = angle;
    }
}
