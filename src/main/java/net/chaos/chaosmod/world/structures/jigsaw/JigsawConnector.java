package net.chaos.chaosmod.world.structures.jigsaw;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class JigsawConnector {
	public BlockPos worldPos;
    public BlockPos localPos;
    public EnumFacing facing;
    public String targetPool;
    public String attachmentType;
    public ResourceLocation turnsInto;
}