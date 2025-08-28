package net.chaos.chaosmod.blocks.abstracted;

import net.chaos.chaosmod.blocks.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockFence extends ItemBlockBase {

	public ItemBlockFence(Block block) {
		super(block);
        this.setHasSubtypes(true);
	}

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + WoodVariants.byMetadata(stack.getMetadata()).getName();
    }
}