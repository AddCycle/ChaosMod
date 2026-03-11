package net.chaos.chaosmod.blocks.decoration;

import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import proxy.IBlockModel;

public class OxoniumStairs extends BlockStairs implements IBlockModel {

	public OxoniumStairs(String name, IBlockState modelState) {
		super(modelState);
		setUnlocalizedName(name);
		setRegistryName(name);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}
}