package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import proxy.IBlockModel;

public class AbstractLadder extends BlockLadder implements IBlockModel {

	public AbstractLadder(String name, Material materialIn) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(0.4F);
		setSoundType(SoundType.LADDER);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}
}