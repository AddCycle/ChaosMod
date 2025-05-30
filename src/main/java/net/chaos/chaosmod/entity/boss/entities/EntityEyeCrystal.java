package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityEyeCrystal extends EntityEnderCrystal {

	public EntityEyeCrystal(World worldIn) {
		super(worldIn);
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}
	
	@Override
	public boolean shouldShowBottom() {
		return false;
	}
	
	@Override
	public BlockPos getBeamTarget() {
		return super.getBeamTarget();
	}
	
	@Override
	public void setBeamTarget(BlockPos beamTarget) {
		// TODO Auto-generated method stub
		super.setBeamTarget(beamTarget);
	}
	
	@Override
	public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
		return super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
	}
}
