package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import util.IHasModel;

public class AbstractFence extends BlockFence implements IHasModel {
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

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}