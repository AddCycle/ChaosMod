package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.lore.dialogs.ITalkable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

public class EntityViking extends EntityMob implements ITalkable {
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
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsTarget(this, 0.2, 20.0f)); // Come to the rescue of player ?
        // this.tasks.addTask(2, new EntityAIAttackMelee(this, 15, false)); // Come to the rescue of player ? To save his resources
        // this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityPicsou>(this, EntityPicsou.class, 10, true, true, null));
        this.targetTasks.addTask(2, new EntityAIFindEntityNearestPlayer(this));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6);
		this.getEntityAttribute(SWIM_SPEED).setBaseValue(10.5);
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
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote)
		{
			player.openGui(Main.instance, Reference.GUI_VIKING_ID, this.world, this.getEntityId(), 0, 0);
			// player.addPotionEffect(new PotionEffectVikingFriends(ModPotions.POTION_VIKING, 20 * 300));
		}
		return true;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return new ResourceLocation(Reference.MODID, "entities/viking");
	}

	@SideOnly(Side.CLIENT)
	public String getDialogText() {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || player.isCreative())
			return "";

		// Checks distance
		if (this.getDistanceSq(player) > 40) {
			return "";
		}

		return "Hello traveler! Want a quest?";
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
		if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.EASY) {
			int i = this.rand.nextInt(2);

			if (i == 0) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.OXONIUM_HALLEBERD));
			} else {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ModBlocks.CUSTOM_FLOWER, 1, 3));
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

		if (d0 > 10.0D) {
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 > 10.0D) {
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 > 10.0D) {
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		if (d0 < -10.0D) {
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 < -10.0D) {
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 < -10.0D) {
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		this.chasingPosX += d0 * 0.25D;
		this.chasingPosZ += d2 * 0.25D;
		this.chasingPosY += d1 * 0.25D;
	}

}
