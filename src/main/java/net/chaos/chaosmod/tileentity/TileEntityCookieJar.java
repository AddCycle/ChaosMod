package net.chaos.chaosmod.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import util.tilentity.ISimpleInventory;

public class TileEntityCookieJar extends TileEntity implements ISimpleInventory {

	@Override
    public int getSize()
    {
        return getBlockType().getMetaFromState(getWorld().getBlockState(pos));
    }

    @Override
    public ItemStack getItem(int i)
    {
        return new ItemStack(Items.COOKIE);
    }

    @Override
    public void clear()
    {
    }
}
