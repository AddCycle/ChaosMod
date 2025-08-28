package net.chaos.chaosmod.blocks.complex.mimic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.IHasModel;

public class ShapeShifterBlock extends Block implements IHasModel {
	public static final UnlistedPropertyCopiedBlock COPIEDBLOCK = new UnlistedPropertyCopiedBlock();

	public ShapeShifterBlock() {
		super(Material.CIRCUITS);
	}

	// render as model-based block

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.SOLID;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {  // avoid crash in case of mismatch
			IExtendedBlockState retval = (IExtendedBlockState)state;
			IBlockState bestAdjacentBlock = selectBestAdjacentBlock(world, pos);
			retval = retval.withProperty(COPIEDBLOCK, bestAdjacentBlock);
			return retval;
		}
		return state;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state;  //for debugging - useful spot for a breakpoint.  Not necessary.
	}

	@Override
	protected BlockStateContainer createBlockState() {
		IProperty [] listedProperties = new IProperty[0]; // no listed properties
		IUnlistedProperty [] unlistedProperties = new IUnlistedProperty[] {COPIEDBLOCK};
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	private IBlockState selectBestAdjacentBlock(IBlockAccess world, BlockPos blockPos)
	{
		final IBlockState UNCAMOUFLAGED_BLOCK = Blocks.AIR.getDefaultState();
		TreeMap<EnumFacing, IBlockState> adjacentSolidBlocks = new TreeMap<EnumFacing, IBlockState>();

		HashMap<IBlockState, Integer> adjacentBlockCount = new HashMap<IBlockState, Integer>();
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos adjacentPosition = blockPos.add(facing.getFrontOffsetX(),
					facing.getFrontOffsetY(),
					facing.getFrontOffsetZ());
			IBlockState adjacentIBS = world.getBlockState(adjacentPosition);
			Block adjacentBlock = adjacentIBS.getBlock();
			if (adjacentBlock != Blocks.AIR
					&& adjacentBlock.getBlockLayer() == BlockRenderLayer.SOLID
					&& adjacentBlock.isOpaqueCube(adjacentIBS)) {
				adjacentSolidBlocks.put(facing, adjacentIBS);
				if (adjacentBlockCount.containsKey(adjacentIBS)) {
					adjacentBlockCount.put(adjacentIBS, 1 + adjacentBlockCount.get(adjacentIBS));
				} else if (adjacentIBS.getBlock() != this){
					adjacentBlockCount.put(adjacentIBS, 1);
				}
			}
		}

		if (adjacentBlockCount.isEmpty()) {
			return UNCAMOUFLAGED_BLOCK;
		}

		if (adjacentSolidBlocks.size() == 1) {
			IBlockState singleAdjacentBlock = adjacentSolidBlocks.firstEntry().getValue();
			if (singleAdjacentBlock.getBlock() == this) {
				return UNCAMOUFLAGED_BLOCK;
			} else {
				return singleAdjacentBlock;
			}
		}

		int maxCount = 0;
		ArrayList<IBlockState> maxCountIBlockStates = new ArrayList<IBlockState>();
		for (Map.Entry<IBlockState, Integer> entry : adjacentBlockCount.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxCountIBlockStates.clear();
				maxCountIBlockStates.add(entry.getKey());
				maxCount = entry.getValue();
			} else if (entry.getValue() == maxCount) {
				maxCountIBlockStates.add(entry.getKey());
			}
		}

		assert maxCountIBlockStates.isEmpty() == false;
		if (maxCountIBlockStates.size() == 1) {
			return maxCountIBlockStates.get(0);
		}

		// for each block which has a match on the opposite side, add 10 to its count.
		// exact matches are counted twice --> +20, match with BlockCamouflage only counted once -> +10
		for (Map.Entry<EnumFacing, IBlockState> entry : adjacentSolidBlocks.entrySet()) {
			IBlockState iBlockState = entry.getValue();
			if (maxCountIBlockStates.contains(iBlockState)) {
				EnumFacing oppositeSide = entry.getKey().getOpposite();
				IBlockState oppositeBlock = adjacentSolidBlocks.get(oppositeSide);
				if (oppositeBlock != null && (oppositeBlock == iBlockState || oppositeBlock.getBlock() == this) ) {
					adjacentBlockCount.put(iBlockState, 10 + adjacentBlockCount.get(iBlockState));
				}
			}
		}

		maxCount = 0;
		maxCountIBlockStates.clear();
		for (Map.Entry<IBlockState, Integer> entry : adjacentBlockCount.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxCountIBlockStates.clear();
				maxCountIBlockStates.add(entry.getKey());
				maxCount = entry.getValue();
			} else if (entry.getValue() == maxCount) {
				maxCountIBlockStates.add(entry.getKey());
			}
		}
		assert maxCountIBlockStates.isEmpty() == false;
		if (maxCountIBlockStates.size() == 1) {
			return maxCountIBlockStates.get(0);
		}

		EnumFacing [] orderOfPreference = new EnumFacing[] {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST,
				EnumFacing.WEST, EnumFacing.DOWN, EnumFacing.UP};

		for (EnumFacing testFace : orderOfPreference) {
			if (adjacentSolidBlocks.containsKey(testFace) &&
					maxCountIBlockStates.contains(adjacentSolidBlocks.get(testFace))) {
				return adjacentSolidBlocks.get(testFace);
			}
		}
		assert false : "this shouldn't be possible";
		return null;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
