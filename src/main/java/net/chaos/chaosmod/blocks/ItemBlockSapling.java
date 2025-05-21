package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.blocks.decoration.CustomLeaves.CustomLeafVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockSapling extends ItemBlockBase {

	public ItemBlockSapling(Block block) {
		super(block);
		this.setHasSubtypes(true);
	}
	
	@Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + CustomLeafVariant.byMetadata(stack.getMetadata()).getName() + "_sapling";
    }

}
