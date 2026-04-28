package net.chaos.chaosmod.blocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * TODO : make tileentity to store data or content & render
 * TODO : make texture & blockstates + model
 */
public class BeehiveBlock extends BlockContainerBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public BeehiveBlock() {
		super("beehive", Material.WOOD);
        this.setDefaultState(
        	this.blockState.getBaseState()
        	.withProperty(FACING, EnumFacing.NORTH)
        	.withProperty(AGE, 0));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING, AGE});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int facing = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex(); // 0-3, 4 values (2bits)
		int age = state.getValue(AGE); // between 0 - 3 requires 2 bits to encode
        return (facing << 2) | age;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		int facing = (meta >> 2) & 3;
		int age = meta & 3;

		return this.getDefaultState()
			.withProperty(FACING, EnumFacing.getHorizontal(facing))
			.withProperty(AGE, age);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState()
			.withProperty(FACING, placer.getHorizontalFacing().getOpposite())
			.withProperty(AGE, 0);
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
	}
}