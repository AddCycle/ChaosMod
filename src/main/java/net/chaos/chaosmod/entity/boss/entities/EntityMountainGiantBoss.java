package net.chaos.chaosmod.entity.boss.entities;

import java.util.List;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMountainGiantBoss extends EntityMob {
    public final BossInfoServer bossInfo;

	public EntityMountainGiantBoss(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		bossInfo = new BossInfoServer(getDisplayName(), Color.BLUE, Overlay.PROGRESS);
		this.bossInfo.setName(getDisplayName());
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}
	
	@Override
	protected void initEntityAI() {
		// this.tasks.addTask(1, new CustomAITest(this, 1.0f));
		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(1, new EntityAIEscapeWater(this, 1.2D));
	    this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
	    this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	    this.tasks.addTask(3, new EntityAILookIdle(this));
	    this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.3, false));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		// super.initEntityAI();
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
	}
	
	@Override
	public void onLivingUpdate() {
		if (this.deathTime <= 0) { 
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
		super.onLivingUpdate();
	}
	
	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	@Override
	protected void onDeathUpdate() {
        ++this.deathTime;

        if (!world.isRemote) {
        	this.motionY = 0.02D; // Constant upward motion
        	this.posY += this.motionY;
        }

        if (this.deathTime >= 200)
        {
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
            {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }


            List<EntityPlayer> nearby_players = null;
            int count = 0;
            if (!world.isRemote) {
            	int radius = 50; // FIXME: boss chamber radius
            	 nearby_players = world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(radius));
            	 count = nearby_players.size();
            }

            if (!world.isRemote) {
            	for (EntityPlayerMP pl : world.getMinecraftServer().getPlayerList().getPlayers()) {
            		pl.sendMessage(new TextComponentTranslation("entity.revenge_blaze_boss.death_message"));
            	}
            }

            this.setDead();

            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
            }
            
            if (!world.isRemote) {
            	world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.OXONIUM_HALLEBERD, count == 0 ? 1 : count))); // FIXME drop the giantHeart + BraveTrophy
            }
        }
        else
        {
        	float progress = 1.0F - (float) this.deathTime / 200.0F;
            this.bossInfo.setPercent(Math.max(progress, 0.0F));
            this.bossInfo.setColor(BossInfo.Color.GREEN);
            this.bossInfo.setName(new TextComponentTranslation("boss.mountain_giant.death")); // FIXME : add localization
        }
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

}
