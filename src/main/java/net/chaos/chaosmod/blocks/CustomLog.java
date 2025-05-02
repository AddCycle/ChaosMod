package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import util.IHasModel;

public class CustomLog extends BlockLog implements IHasModel {
	
	public CustomLog(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModTabs.GENERAL_TAB);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
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
	    return this.getDefaultState().withProperty(LOG_AXIS, axis);
	}

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LOG_AXIS).ordinal() << 2;
    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}