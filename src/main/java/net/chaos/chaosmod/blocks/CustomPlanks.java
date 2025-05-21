package net.chaos.chaosmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import util.IHasModel;

public class CustomPlanks extends Block implements IHasModel {
	public static final PropertyEnum<CustomPlankVariant> VARIANT = PropertyEnum.create("variant", CustomPlankVariant.class);
	
	public CustomPlanks(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setDefaultState(
			this.blockState.getBaseState().withProperty(VARIANT, CustomPlankVariant.SNOWY));
										  
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockPlanks(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (CustomPlankVariant type : CustomPlankVariant.values()) {
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
	    CustomPlankVariant variant = CustomPlankVariant.byMetadata(variantMeta);
	    // return this.getDefaultState().withProperty(VARIANT, variant).withProperty(LOG_AXIS, axis);
	    return this.getDefaultState().withProperty(VARIANT, variant);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    int meta = state.getValue(VARIANT).getMeta();
	    // meta |= state.getValue(LOG_AXIS).ordinal() << 2;
	    return meta;
	}

	@Override
	public void registerModels() {
		for (CustomPlankVariant type : CustomPlankVariant.values()) {
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), type.getMeta(), type.getName(), "inventory");
		}
	}
	
	public enum CustomPlankVariant implements IStringSerializable {
        SNOWY(0, "snowy_plank"),
        MAPLE(1, "maple_plank"),
        ENDER(2, "ender_plank"),
        OLIVE(3, "olive_plank");

    	private static final CustomPlankVariant[] META_LOOKUP = new CustomPlankVariant[values().length];
        private final int meta;
        private final String name;

        CustomPlankVariant(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() {
            return this.meta;
        }

        public static CustomPlankVariant byMetadata(int meta) {
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
        	for (CustomPlankVariant type : values()) {
        		META_LOOKUP[type.getMeta()] = type;
        	}
        }
    }

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<>();
	    int meta = this.getMetaFromState(state);
	    drops.add(new ItemStack(Item.getItemFromBlock(this), 1, meta));
	    return drops;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// TODO Auto-generated method stub
		return super.getItemDropped(state, rand, fortune);
	}

}
