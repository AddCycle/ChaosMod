package net.chaos.chaosmod.world.structures;

import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionProvider extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.CUSTOM;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new DimensionGenerator(world, world.getSeed(), true, world.getWorldInfo().getGeneratorOptions());
	}
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		return 2;
	}
	
	

}
