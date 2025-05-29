package net.chaos.chaosmod.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityBeam extends TileEntity {
    public float beamHeight = 70f;
    
    @Override
    public boolean shouldRenderInPass(int pass) {
    	return true;
    }
}