package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import util.Reference;

public class TileEntityJigsaw extends TileEntity {
	private String author = "";

	public boolean usedBy(EntityPlayer playerIn) {
		if (!playerIn.canUseCommandBlock()) {
			return false;
		}
		if (world.isRemote) {
			 playerIn.openGui(Main.instance, Reference.GUI_JIGSAW_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	public void createdBy(EntityLivingBase placer) {
		if (!StringUtils.isNullOrEmpty(placer.getName())) {
			this.author = placer.getName();
		}
	}
}