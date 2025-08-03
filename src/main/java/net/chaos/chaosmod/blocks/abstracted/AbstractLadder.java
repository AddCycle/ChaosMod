package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import util.IHasModel;

public class AbstractLadder extends BlockLadder implements IHasModel {

	public AbstractLadder(String name, Material materialIn) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(0.4F);
		setSoundType(SoundType.LADDER);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
