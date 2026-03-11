package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import proxy.IBlockModel;

public class AbstractFence extends BlockFence implements IBlockModel {
	public AbstractFence(String name, Material materialIn, MapColor mapColorIn) {
		super(materialIn, mapColorIn);

		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(MaterialHelper.getSoundFromMaterial(materialIn));
		MaterialHelper.applyMaterialProperties(this, materialIn);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
}