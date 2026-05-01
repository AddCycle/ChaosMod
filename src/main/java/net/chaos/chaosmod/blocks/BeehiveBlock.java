package net.chaos.chaosmod.blocks;

import java.util.Random;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * TODO : make tileentity to store data or content honey buckets production & render
 * TODO : make texture & blockstates + model (other states than base one)
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
        this.setTickRandomly(true);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.isEmpty()) return false;

		if (!worldIn.isRemote) {
			if (stack.getItem() instanceof ItemGlassBottle) {
				
			}
		}
		
		// FIXME : make a honey bottle that can be filled with this liquid
		
		return false;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		int i = this.getAge(state);
		EnumFacing facing = this.getFacing(state);
		if (this.isMaxAge(state)) return;

		boolean produced = rand.nextFloat() >= 0.8f;

		if (produced) worldIn.setBlockState(pos, this.withProperties(i + 1, facing), 2);
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

    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    protected PropertyDirection getFacingProperty()
    {
        return FACING;
    }

    protected EnumFacing getFacing(IBlockState state)
    {
        return state.getValue(this.getFacingProperty());
    }

    public int getMaxAge()
    {
        return 3;
    }

    protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public IBlockState withFacing(EnumFacing facing)
    {
        return this.getDefaultState().withProperty(this.getFacingProperty(), facing);
    }
    
    public IBlockState withProperties(int age, EnumFacing facing)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age)).withProperty(this.getFacingProperty(), facing);
    }

    public boolean isMaxAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }
}