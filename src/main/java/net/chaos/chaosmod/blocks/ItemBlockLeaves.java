package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.blocks.decoration.CustomLeaves.CustomLeafVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockLeaves extends ItemBlock {

	public ItemBlockLeaves(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + CustomLeafVariant.byMetadata(stack.getMetadata()).getName();
    }

}
