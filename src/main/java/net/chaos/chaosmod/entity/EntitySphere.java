package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntitySphere extends Entity implements IRightClickableEntity {

	public EntitySphere(World worldIn) {
		super(worldIn);
		this.setSize(2f, 2f);
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
	        Main.getLogger().info("Sphere interaction");
	        player.sendMessage(new TextComponentString("Destroyed Black Hole catalyst"));
	        this.setDead();
	    }
	    return true;
	}
	
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(
            posX, posY, posZ,
            posX - 1, posY + 3, posZ + 1
        );
	}
	
	@Override
	public boolean canBeCollidedWith() {
	    return true;
	}
}