package net.chaos.chaosmod.world.structures;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ChestInfo {
	public final BlockPos pos;
	public final ResourceLocation loot;

	public ChestInfo(BlockPos pos, ResourceLocation loot) {
		this.pos = pos;
		this.loot = loot;
	}
}
