package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import util.Reference;

public class TileEntityJigsaw extends TileEntity {
	private String author = "";
	private String pool = "minecraft:empty";
	private String attachementType = "minecraft:empty";
	private String turnsInto = "minecraft:air";

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("author", this.author);
		compound.setString("pool", this.pool);
		compound.setString("attachementType", this.attachementType);
		compound.setString("turnsInto", this.turnsInto);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.author = compound.getString("author");
		this.pool = compound.getString("pool");
		this.attachementType = compound.getString("attachementType");
		this.turnsInto = compound.getString("turnsInto");
	}

	@Override
	public NBTTagCompound getUpdateTag() { return this.writeToNBT(new NBTTagCompound()); }

	public boolean usedBy(EntityPlayer playerIn) {
		if (!playerIn.canUseCommandBlock()) {
			return false;
		}

		if (world.isRemote) {
			playerIn.openGui(Main.instance, Reference.GUI_JIGSAW_ID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
	    return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	    this.readFromNBT(pkt.getNbtCompound());
	}

	public void createdBy(EntityLivingBase placer) {
		if (!StringUtils.isNullOrEmpty(placer.getName())) {
			this.author = placer.getName();
		}
	}

	public void setTurnsInto(String turnsInto) { this.turnsInto = turnsInto; }

	public String getTurnsInto() { return turnsInto; }

	public void setAttachementType(String attachementType) { this.attachementType = attachementType; }

	public String getAttachementType() { return attachementType; }

	public void setPool(String pool) { this.pool = pool; }

	public String getPool() { return pool; }
}