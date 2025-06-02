package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockDoor;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import util.IHasModel;

public class AbstractDoor extends BlockDoor implements IHasModel {

	public AbstractDoor(String name, Material materialIn) {
		super(materialIn);
		// TODO : make it in ModBlocks to allow iron materials type
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(3.0F);
		setSoundType(SoundType.WOOD);
		disableStats();

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockDoor(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
