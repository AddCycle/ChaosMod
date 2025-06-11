package net.chaos.chaosmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import util.IHasModel;

public class CustomLog extends BlockLog implements IHasModel {
	public static final PropertyEnum<CustomLogVariant> VARIANT = PropertyEnum.create("variant", CustomLogVariant.class);
	
	public CustomLog(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setDefaultState(
			this.blockState.getBaseState().withProperty(VARIANT, CustomLogVariant.SNOWY).withProperty(LOG_AXIS, EnumAxis.Y));
										  
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockLog(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (CustomLogVariant type : CustomLogVariant.values()) {
            list.add(new ItemStack(Item.getItemFromBlock(this), 1, type.getMeta()));
        }
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    EnumAxis axis;
	    switch (meta & 0b1100) {
	        case 0b0100: axis = EnumAxis.X; break;
	        case 0b1000: axis = EnumAxis.Z; break;
	        case 0b1100: axis = EnumAxis.NONE; break;
	        default:     axis = EnumAxis.Y;
	    }
	    int variantMeta = meta & 0b0011;
	    CustomLogVariant variant = CustomLogVariant.byMetadata(variantMeta);
	    return this.getDefaultState().withProperty(VARIANT, variant).withProperty(LOG_AXIS, axis);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    int meta = state.getValue(VARIANT).getMeta();
	    meta |= state.getValue(LOG_AXIS).ordinal() << 2;
	    return meta;
	}

	@Override
	public void registerModels() {
		for (CustomLogVariant type : CustomLogVariant.values()) {
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), type.getMeta(), type.getName(), "inventory");
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
	    int meta = state.getValue(VARIANT).getMeta();
		return new ItemStack(Item.getItemFromBlock(this), 1, meta).getItem();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		int meta = state.getValue(VARIANT).getMeta();
		return new ItemStack(Item.getItemFromBlock(this), 1, meta);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<>();
		int meta = state.getValue(VARIANT).getMeta();
	    drops.add(new ItemStack(Item.getItemFromBlock(this), 1, meta));
	    return drops;
	}
	
	public enum CustomLogVariant implements IStringSerializable {
        SNOWY(0, "snowy_log"),
        MAPLE(1, "maple_log"),
        ENDER(2, "ender_log"),
        OLIVE(3, "olive_log");

    	private static final CustomLogVariant[] META_LOOKUP = new CustomLogVariant[values().length];
        private final int meta;
        private final String name;

        CustomLogVariant(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() {
            return this.meta;
        }

        public static CustomLogVariant byMetadata(int meta) {
        	if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        static {
        	for (CustomLogVariant type : values()) {
        		META_LOOKUP[type.getMeta()] = type;
        	}
        }
    }

}