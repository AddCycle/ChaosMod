package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.blocks.CustomLog.CustomLogVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockLog extends ItemBlockBase {

	public ItemBlockLog(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.block.getUnlocalizedName() + "." + CustomLogVariant.byMetadata(stack.getMetadata()).getName();
    }

}
