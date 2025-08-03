package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import util.IHasModel;

public class AbstractBreakableBlock extends BlockBreakable implements IHasModel {

	protected AbstractBreakableBlock(String name, Material materialIn, boolean ignoreSimilarityIn) {
		super(materialIn, ignoreSimilarityIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(-1.0F);
		setSoundType(SoundType.GLASS);
		setLightLevel(0.75F);
	
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
