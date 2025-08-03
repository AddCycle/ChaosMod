package net.chaos.chaosmod.blocks.abstracted;

import java.util.Random;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.ItemBlockDoor;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import util.IHasModel;

public class AbstractDoor extends BlockDoor implements IHasModel {

	public AbstractDoor(String name, Material materialIn) {
		super(materialIn);
		// TODO : make it in ModBlocks to allow iron materials type
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(3.0F);
		setSoundType(materialIn == Material.WOOD ? SoundType.WOOD : SoundType.METAL);
		disableStats();

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockDoor(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
