package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
public class EntityViking extends EntityMob {
	public boolean angry = false;
	public double prevChasingPosX;
	public double prevChasingPosY;
	public double prevChasingPosZ;
	public double chasingPosX;
	public double chasingPosY;
	public double chasingPosZ;
	public float prevCameraYaw;
	public float cameraYaw;

	public EntityViking(World worldIn) {
		super(worldIn);
        this.setSize(1.0F, 2.0F); // Set dimensions appropriately
        this.experienceValue = 500;
        this.setGlowing(true);
        this.setCanPickUpLoot(true);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setEquipmentBasedOnDifficulty(difficulty);
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
    @Override
    protected void initEntityAI() {
    	super.initEntityAI();
    }
    
    @Override
    public boolean hitByEntity(Entity entityIn) {
    	if (entityIn instanceof EntityPlayer) {
    		this.angry = true;
    		this.updateCape();
    	}
    	entityIn.onKillCommand();
    	return true;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return new ResourceLocation(Reference.MODID, "entities/viking");
    }

    @SideOnly(Side.CLIENT)
    public String getDialogText() {
    	// System.out.println("Method used");
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player == null || player.isCreative()) return "";

    	// System.out.println("Method used");
        // Optional distance check
        if (this.getDistanceSq(player) > 40) return "";

    	// System.out.println("Method used");
        // Ray trace from player’s eyes
        RayTraceResult ray = Minecraft.getMinecraft().objectMouseOver;

        // Check if player is pointing at this entity
        //if (ray != null && ray.typeOfHit == RayTraceResult.Type.ENTITY && ray.entityHit == this) {
        	// System.out.println("Method used");
        return "Hello traveler! Want a quest?";
        //}

        //return "";
    }
    
    @Override
    public boolean isSpectatedByPlayer(EntityPlayerMP player) {
    	return super.isSpectatedByPlayer(player);
    }
    
    @Override
    public ItemStack getHeldItemMainhand() {
    	return super.getHeldItemMainhand();
    }
    
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    	super.setEquipmentBasedOnDifficulty(difficulty);
        if (true)
        {
            int i = this.rand.nextInt(2);

            if (i == 0)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
            else
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModBlocks.CUSTOM_FLOWER, 1, 1));
            }
        }
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	this.updateCape();
    }

	private void updateCape() {
		this.prevChasingPosX = this.chasingPosX;
		this.prevChasingPosY = this.chasingPosY;
		this.prevChasingPosZ = this.chasingPosZ;
		double d0 = this.posX - this.chasingPosX;
		double d1 = this.posY - this.chasingPosY;
		double d2 = this.posZ - this.chasingPosZ;
		double d3 = 10.0D;

		if (d0 > 10.0D)
		{
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 > 10.0D)
		{
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 > 10.0D)
		{
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		if (d0 < -10.0D)
		{
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 < -10.0D)
		{
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 < -10.0D)
		{
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		this.chasingPosX += d0 * 0.25D;
		this.chasingPosZ += d2 * 0.25D;
		this.chasingPosY += d1 * 0.25D;
	}

}
