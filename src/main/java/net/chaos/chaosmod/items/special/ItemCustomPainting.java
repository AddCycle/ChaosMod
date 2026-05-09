package net.chaos.chaosmod.items.special;

import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.painting.EntityCustomPainting;
import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import proxy.IItemModel;

public class ItemCustomPainting extends ItemBase implements IItemModel {

	public ItemCustomPainting() {
		super("custom_painting");
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        BlockPos blockpos = pos.offset(facing);

        if (facing != EnumFacing.DOWN && facing != EnumFacing.UP && player.canPlayerEdit(blockpos, facing, itemstack))
        {
            EntityCustomPainting entitypainting = this.createEntity(worldIn, blockpos, facing);

            if (entitypainting != null && entitypainting.onValidSurface())
            {
                if (!worldIn.isRemote)
                {
                    entitypainting.playPlaceSound();
                    worldIn.spawnEntity(entitypainting);
                }

                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
	}

	@Nullable
	private EntityCustomPainting createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
		return new EntityCustomPainting(worldIn, pos, clickedSide);
	}
}