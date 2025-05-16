package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.blocks.CustomLog.CustomLogVariant;
import net.chaos.chaosmod.blocks.CustomPlanks.CustomPlankVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPlanks extends ItemBlock {
	public ItemBlockPlanks(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + CustomPlankVariant.byMetadata(stack.getMetadata()).getName();
    }

}
