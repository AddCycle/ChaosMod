package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import proxy.IBlockModel;

public class AbstractBreakableBlock extends BlockBreakable implements IBlockModel {

	protected AbstractBreakableBlock(String name, Material materialIn, boolean ignoreSimilarityIn) {
		super(materialIn, ignoreSimilarityIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setBlockUnbreakable();
		setSoundType(SoundType.GLASS);
		setLightLevel(0.75F);
	
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
}