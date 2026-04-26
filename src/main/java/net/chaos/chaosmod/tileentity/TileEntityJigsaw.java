package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import util.Reference;

public class TileEntityJigsaw extends TileEntity {
	private String author = "";
	private Mirror mirror = Mirror.NONE;
	private Rotation rotation = Rotation.NONE;
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
		compound.setString("mirror", this.mirror.name());
		compound.setString("rotation", this.rotation.name());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.author = compound.getString("author");
		this.pool = compound.getString("pool");
		this.attachementType = compound.getString("attachementType");
		this.turnsInto = compound.getString("turnsInto");
		try {
			this.mirror = Mirror.valueOf(compound.getString("mirror"));
		} catch (IllegalArgumentException e) {
			this.mirror = Mirror.NONE;
		}
		try {
			this.rotation = Rotation.valueOf(compound.getString("rotation"));
		} catch (IllegalArgumentException e) {
			this.rotation = Rotation.NONE;
		}
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

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

	public void setMirror(Mirror mirror) { this.mirror = mirror; }

	public void setRotation(Rotation rotation) { this.rotation = rotation; }

	public void setTurnsInto(String turnsInto) { this.turnsInto = turnsInto; }

	public String getTurnsInto() { return turnsInto; }

	public void setAttachementType(String attachementType) { this.attachementType = attachementType; }

	public String getAttachementType() { return attachementType; }

	public void setPool(String pool) { this.pool = pool; }

	public String getPool() { return pool; }
}