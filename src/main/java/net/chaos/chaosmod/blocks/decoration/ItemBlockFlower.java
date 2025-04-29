package net.chaos.chaosmod.blocks.decoration;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFlower extends ItemBlock {

	public ItemBlockFlower(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + FlowerType.byMetadata(stack.getMetadata()).getName();
    }
}
