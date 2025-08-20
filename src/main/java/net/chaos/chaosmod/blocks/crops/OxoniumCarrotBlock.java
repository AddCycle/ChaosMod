package net.chaos.chaosmod.blocks.crops;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import util.IHasModel;

public class OxoniumCarrotBlock extends BlockCrops implements IHasModel, IPlantable {
	private static final AxisAlignedBB[] CARROT_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)};
	
	public OxoniumCarrotBlock(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add((new ItemBlockBase(this) {
			@Override
			public CreativeTabs[] getCreativeTabs() {
				return new CreativeTabs[] {};
			}
		}).setRegistryName(this.getRegistryName()));
	}

	@Override
    protected Item getSeed()
    {
        return ModItems.OXONIUM_CARROT;
    }

	@Override
    protected Item getCrop()
    {
        return ModItems.OXONIUM_CARROT;
    }
	
	@Override
	// INFO & FIXING : the issue crop isn't dropping
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CARROT_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
    
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.FARMLAND;
    }
    
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
