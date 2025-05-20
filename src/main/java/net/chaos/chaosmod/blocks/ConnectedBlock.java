package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import util.IHasModel;

public class ConnectedBlock extends Block implements IHasModel {
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool EAST  = PropertyBool.create("east");
    public static final PropertyBool WEST  = PropertyBool.create("west");
    public static final PropertyBool UP    = PropertyBool.create("up");
    public static final PropertyBool DOWN  = PropertyBool.create("down");
    public static final PropertyBool MAIN  = PropertyBool.create("a");
    // 0 = up, 1 = down, 2 = right, 3 = left (is there an air block)
    // public static final PropertyInteger FACE_CORNERS = PropertyInteger.create("a", 0, 3);

    public ConnectedBlock(String name, Material material) {
    	super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setDefaultState(this.blockState.getBaseState()
            .withProperty(NORTH, false)
            .withProperty(SOUTH, false)
            .withProperty(EAST, false)
            .withProperty(WEST, false)
            .withProperty(UP, false)
            .withProperty(DOWN, false)
            .withProperty(MAIN, false));
        
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block block = this;
        Block main = ModBlocks.FORGE_INTERFACE_BLOCK;
        return state
            .withProperty(NORTH, worldIn.getBlockState(pos.north()).getBlock() == block)
            .withProperty(SOUTH, worldIn.getBlockState(pos.south()).getBlock() == block)
            .withProperty(EAST,  worldIn.getBlockState(pos.east()).getBlock() == block)
            .withProperty(WEST,  worldIn.getBlockState(pos.west()).getBlock() == block)
            .withProperty(UP,  worldIn.getBlockState(pos.up()).getBlock() == block)
            .withProperty(DOWN,  worldIn.getBlockState(pos.down()).getBlock() == block)
            .withProperty(MAIN,  worldIn.getBlockState(pos.down()).getBlock() == main
            || worldIn.getBlockState(pos.up()).getBlock() == main
        	|| worldIn.getBlockState(pos.north()).getBlock() == main
        	|| worldIn.getBlockState(pos.south()).getBlock() == main
        	|| worldIn.getBlockState(pos.east()).getBlock() == main
        	|| worldIn.getBlockState(pos.west()).getBlock() == main);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, SOUTH, EAST, WEST, UP, DOWN, MAIN);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState(); // no meta-based state
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}