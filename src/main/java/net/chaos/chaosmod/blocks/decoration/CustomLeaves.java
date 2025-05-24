package net.chaos.chaosmod.blocks.decoration;

import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockLeaves;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import util.IHasModel;

public class CustomLeaves extends BlockLeaves implements IHasModel {
	public static final PropertyEnum<CustomLeafVariant> VARIANT = PropertyEnum.create("variant", CustomLeafVariant.class);
    public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	
	public CustomLeaves(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setDefaultState(this.blockState.getBaseState()
	            .withProperty(VARIANT, CustomLeafVariant.SNOWY)
	            .withProperty(DECAYABLE, true)
	            .withProperty(CHECK_DECAY, false));
		setSoundType(SoundType.PLANT);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockLeaves(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
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
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        IBlockState state = world.getBlockState(pos);
        return Collections.singletonList(new ItemStack(this, 1, state.getValue(VARIANT).getMeta()));
    }

	@Override
	public void registerModels() {
		for (CustomLeafVariant type : CustomLeafVariant.values()) {
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), type.getMeta(), type.getName(), "inventory");
		}
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, CustomLeafVariant.byMetadata(meta & 3))
            .withProperty(DECAYABLE, (meta & 4) == 0)
            .withProperty(CHECK_DECAY, (meta & 8) != 0);
    }

	@Override
	public int getMetaFromState(IBlockState state) {
	    int meta = state.getValue(VARIANT).getMeta();
	    if (!state.getValue(DECAYABLE)) meta |= 4;
	    if (state.getValue(CHECK_DECAY)) meta |= 8;
	    return meta;
	}
    
    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (CustomLeafVariant type : CustomLeafVariant.values()) {
            list.add(new ItemStack(Item.getItemFromBlock(this), 1, type.getMeta()));
        }
    }

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT, DECAYABLE, CHECK_DECAY);
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public enum CustomLeafVariant implements IStringSerializable {
        SNOWY(0, "snowy"),
        MAPLE(1, "maple"),
        ENDER(2, "ender"),
        OLIVE(3, "olive");

    	private static final CustomLeafVariant[] META_LOOKUP = new CustomLeafVariant[values().length];
        private final int meta;
        private final String name;

        CustomLeafVariant(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() {
            return this.meta;
        }

        public static CustomLeafVariant byMetadata(int meta) {
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
        	for (CustomLeafVariant type : values()) {
        		META_LOOKUP[type.getMeta()] = type;
        	}
        }
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.OAK;
    }

}
