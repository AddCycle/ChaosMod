package net.chaos.chaosmod.blocks;

import java.util.Random;

import net.chaos.chaosmod.entity.animal.EntityBee;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityBeehive;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeehiveBlock extends BlockContainerBase {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

	public BeehiveBlock() {
		super("beehive", Material.WOOD);
        this.setDefaultState(
        	this.blockState.getBaseState()
        	.withProperty(FACING, EnumFacing.NORTH)
        	.withProperty(AGE, 0));
        this.setTickRandomly(true);
        setSoundType(SoundType.WOOD);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) return false;
		
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.isEmpty() || stack.getItem() != Items.GLASS_BOTTLE) return false;
		
		int age = state.getValue(AGE);
		EnumFacing blockfacing = state.getValue(FACING);
		if (age == 0 || facing != blockfacing) return false;

		if (!worldIn.isRemote) {
			worldIn.setBlockState(pos, this.withProperties(age - 1, blockfacing));
		}

		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		
		if (!worldIn.isRemote) {
			ItemStack filledbottle = turnBottleIntoItem(stack, playerIn, new ItemStack(ModItems.HONEY_BOTTLE));
			playerIn.setHeldItem(hand, filledbottle);
		}
		
		return true;
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		int i = this.getAge(state);
		EnumFacing facing = this.getFacing(state);

		tickAge(worldIn, pos, state, rand, facing, i);
	
		tickBeeSpawn(worldIn, pos, state, rand, facing, i);
	}
	
	private void tickAge(World world, BlockPos pos, IBlockState state, Random rand, EnumFacing facing, int age) {
		if (this.isMaxAge(state)) return;

		boolean produced = rand.nextFloat() >= 0.8f;

		if (produced) world.setBlockState(pos, this.withProperties(age + 1, facing), 2);
	}

	private void tickBeeSpawn(World worldIn, BlockPos pos, IBlockState state, Random rand, EnumFacing facing, int age) {
		if (!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityBeehive) {
				TileEntityBeehive beehivetile = (TileEntityBeehive) te;
				if (beehivetile.getBeeCount() > 0 && worldIn.isDaytime()) {
					EntityBee bee = new EntityBee(worldIn);
					BlockPos vec3 = pos.offset(facing);
					float yaw = facing.getHorizontalAngle();
					float pitch = 0.0f;
					bee.setPositionAndRotation(vec3.getX() + 0.5, vec3.getY(), vec3.getZ() + 0.5, yaw, pitch);
					bee.setHome(pos);
					worldIn.spawnEntity(bee);
					beehivetile.setBeeCount(beehivetile.getBeeCount() - 1);
					beehivetile.markDirty();
				}
			}
		}
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBeehive();
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
		int facing = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex(); // 0-2, 3 values (2bits)
		int age = state.getValue(AGE); // between 0 - 2 requires 2 bits to encode
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
        return 2;
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

    public static ItemStack turnBottleIntoItem(ItemStack bottlestack, EntityPlayer player, ItemStack stack)
    {
        bottlestack.shrink(1);

        if (bottlestack.isEmpty())
        {
            return stack;
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(stack))
            {
                player.dropItem(stack, false);
            }

            return bottlestack;
        }
    }
}