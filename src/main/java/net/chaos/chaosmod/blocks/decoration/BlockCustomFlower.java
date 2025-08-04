package net.chaos.chaosmod.blocks.decoration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import util.IHasModel;

public class BlockCustomFlower extends BlockBush implements IHasModel {
	public static final PropertyEnum<FlowerType> type = PropertyEnum.create("variant", FlowerType.class);

	public BlockCustomFlower(String name)
	{
		super();
		setRegistryName(name);
		setUnlocalizedName(name);
		setSoundType(SoundType.PLANT);
		setCreativeTab(ModTabs.GENERAL_TAB);
		setDefaultState(this.blockState.getBaseState().withProperty(type, FlowerType.SNOW_FLOWER));

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockFlower(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.END_STONE || state.getBlock() == Blocks.NETHERRACK || state.getBlock() == Blocks.SNOW
				|| super.canSustainBush(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(type).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(type, FlowerType.byMetadata(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, type);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (FlowerType type : FlowerType.values()) {
			items.add(new ItemStack(this, 1, type.getMeta()));
		}
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
	    if (!worldIn.isRemote) {
	    	int meta = getMetaFromState(state);
	        if (tool != null && tool.getItem() instanceof ItemShears) {
	            // Drop your custom flower item
	            spawnAsEntity(worldIn, pos, new ItemStack(this, 1, meta));
	        } else {
	            // Do nothing, or call super to drop nothing (optional)
	        	ItemStack rareDrop = new ItemStack(getRareDropFromMeta(meta));
	        	float baseChance = 0.2f; // 20% base chance
	        	float fortuneBonus = 0.1f; // +10% per fortune level
	        	float totalChance = baseChance + fortuneBonus;

	        	// Clamp to 100%
	        	if (totalChance > 1.0f) totalChance = 1.0f;

	        	if (player.getEntityWorld().rand.nextFloat() < totalChance) {
	        		spawnAsEntity(worldIn, pos, rareDrop);
	        	}
	        }
	    }
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
	    ItemStack held = player.getHeldItemMainhand();
	    if (held.getItem() instanceof ItemShears) {
	        return 0.5f; // Break instantly
	    }
	    return super.getPlayerRelativeBlockHardness(state, player, world, pos);
	}

	private Item getRareDropFromMeta(int meta) {
		switch (meta) {
		case 0:
			return ModItems.OXONIUM_NUGGET;
		case 1:
			return ModItems.OXONIUM_NUGGET;
		case 2:
			return ModItems.OXONIUM_NUGGET;
		case 3:
			return ModItems.OXONIUM_NUGGET;
		default:
			return ModItems.OXONIUM_NUGGET;
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}

	@Override
	public void registerModels() {
		for (FlowerType type : FlowerType.values()) {
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), type.getMeta(), type.getName(), "inventory");
		}
	}

}
