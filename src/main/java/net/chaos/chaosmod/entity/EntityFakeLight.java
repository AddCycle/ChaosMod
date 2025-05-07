package net.chaos.chaosmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFakeLight extends Entity {
	public EntityFakeLight(World world) {
        super(world);
        this.setSize(0.0F, 0.0F); // Invisible
        this.noClip = true;
        this.setInvisible(true);
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return false; // Prevent rendering
    }

    @Override
    public int getBrightnessForRender() {
        return 0xF000F0; // Max brightness
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

}