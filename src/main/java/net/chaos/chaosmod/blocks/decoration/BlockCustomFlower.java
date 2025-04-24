package net.chaos.chaosmod.blocks.decoration;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import util.IHasModel;

public class BlockCustomFlower extends BlockFlower implements IHasModel {

    protected PropertyEnum<BlockCustomFlower.EnumFlowerType> type;

    public BlockCustomFlower()
    {
    	setRegistryName("custom_flower");
    	setUnlocalizedName("custom_flower");
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getTypeProperty(), this.getBlockType() == BlockCustomFlower.EnumFlowerColor.RED ? BlockCustomFlower.EnumFlowerType.POPPY : BlockCustomFlower.EnumFlowerType.DANDELION));
        
        ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

    public int damageDropped(IBlockState state)
    {
        return ((BlockCustomFlower.EnumFlowerType)state.getValue(this.getTypeProperty())).getMeta();
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (BlockCustomFlower.EnumFlowerType blockflower$enumflowertype : BlockCustomFlower.EnumFlowerType.getTypes(this.getBlockType()))
        {
            items.add(new ItemStack(this, 1, blockflower$enumflowertype.getMeta()));
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(this.getTypeProperty(), BlockCustomFlower.EnumFlowerType.getType(this.getBlockType(), meta));
    }

    public IProperty<BlockCustomFlower.EnumFlowerType> getTypeProperty()
    {
        if (this.type == null)
        {
            this.type = PropertyEnum.<BlockCustomFlower.EnumFlowerType>create("type", BlockCustomFlower.EnumFlowerType.class, new Predicate<BlockCustomFlower.EnumFlowerType>()
            {
                public boolean apply(@Nullable BlockCustomFlower.EnumFlowerType p_apply_1_)
                {
                    return p_apply_1_.getBlockType() == BlockCustomFlower.this.getBlockType();
                }
            });
        }

        return this.type;
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((BlockCustomFlower.EnumFlowerType)state.getValue(this.getTypeProperty())).getMeta();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {this.getTypeProperty()});
    }

	@Override
	public void registerModels() {
		for (int i = 0; i < EnumFlowerType.values().length; i++) {
		    Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), i, "type=" + EnumFlowerType.values()[i].getName());
		}
	}

	@Override
	public EnumFlowerColor getBlockType() {
		return EnumFlowerColor.YELLOW;
	}

}
