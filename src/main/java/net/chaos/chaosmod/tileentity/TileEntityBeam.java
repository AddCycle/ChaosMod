package net.chaos.chaosmod.tileentity;

import javax.annotation.Nonnull;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityBeam extends TileEntity {
    public float beamHeight = 70f;
    
    @Override
    public boolean shouldRenderInPass(int pass) {
    	return true;
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
    	return 65536.0D;
    }
    
    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
            pos.getX(), pos.getY(), pos.getZ(),
            pos.getX() + 1, pos.getY() + 256, pos.getZ() + 1
        );
    }
}