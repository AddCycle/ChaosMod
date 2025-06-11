package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFakeLight extends Entity {

	public EntityFakeLight(World world) {
        super(world);
        this.setSize(0F, 0F); // Invisible
        this.noClip = true;
        // this.setInvisible(true);
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    public boolean isInRangeToRenderDist(double distance) {
        return true; // Prevent rendering
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
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        /*BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        if (world.isAirBlock(pos) || world.getBlockState(pos).getBlock() != ModBlocks.FAKE_LIGHT_BLOCK) {
            world.setBlockState(pos, ModBlocks.FAKE_LIGHT_BLOCK.getDefaultState(), 3);
        }*/
    }

}