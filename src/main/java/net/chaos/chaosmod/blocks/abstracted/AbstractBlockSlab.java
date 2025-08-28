package net.chaos.chaosmod.blocks.abstracted;

import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.IHasModel;

public abstract class AbstractBlockSlab extends BlockSlab implements IHasModel {
	public static final PropertyEnum<AbstractBlockSlab.Variant> VARIANT = PropertyEnum.<AbstractBlockSlab.Variant>create("variant", AbstractBlockSlab.Variant.class);
	public Block halfSlab;

	public AbstractBlockSlab(String name, Material material, MapColor mapColor, Block halfSlab) {
		super(material, mapColor);
		this.halfSlab = halfSlab;
		setUnlocalizedName(name);
		setRegistryName(name);
		IBlockState iblockstate = this.blockState.getBaseState();

		if (!this.isDouble())
		{
			iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}

		this.setDefaultState(iblockstate.withProperty(VARIANT, AbstractBlockSlab.Variant.DEFAULT));
		MaterialHelper.applyMaterialProperties(this, material);
		setSoundType(MaterialHelper.getSoundFromMaterial(material));
		
		ModBlocks.BLOCKS.add(this);
		
		/*if (!this.isDouble()) {
		    ModItems.ITEMS.add(new ItemSlab(this, (BlockSlab) ModBlocks.ABSTRACT_BLOCK_SLAB_HALF, (BlockSlab) ModBlocks.ABSTRACT_BLOCK_SLAB_DOUBLE)
		            .setRegistryName(this.getRegistryName()));
		}*/
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, AbstractBlockSlab.Variant.DEFAULT);

		if (!this.isDouble())
		{
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
		{
			i |= 8;
		}

		return i;
	}

	protected BlockStateContainer createBlockState()
	{
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
	}

	/**
	 * Returns the slab block name with the type associated with it
	 */
	public String getUnlocalizedName(int meta)
	{
		return super.getUnlocalizedName();
	}

	public IProperty<?> getVariantProperty()
	{
		return VARIANT;
	}

	public Comparable<?> getTypeForItem(ItemStack stack)
	{
		return AbstractBlockSlab.Variant.DEFAULT;
	}

	public static class Double extends AbstractBlockSlab
	{
		public Double(String name, Material material, MapColor mapColor, Block halfSlab) {
			super(name, material, mapColor, halfSlab);
		}

		public boolean isDouble()
		{
			return true;
		}
	}

	public static class Half extends AbstractBlockSlab
	{
		public Half(String name, Material material, MapColor mapColor) {
			super(name, material, mapColor, null);
		}

		public boolean isDouble()
		{
			return false;
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
	    return Item.getItemFromBlock(halfSlab); // always drop half slab
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
	    return new ItemStack(halfSlab);
	}

	public static enum Variant implements IStringSerializable
	{
		DEFAULT;

		public String getName()
		{
			return "default";
		}
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
