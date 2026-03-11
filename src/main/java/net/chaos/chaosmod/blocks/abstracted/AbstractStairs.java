package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import proxy.IBlockModel;

public class AbstractStairs extends BlockStairs implements IBlockModel {

	public AbstractStairs(String name, IBlockState modelState) {
		super(modelState);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}
}