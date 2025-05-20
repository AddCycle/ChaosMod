package net.chaos.chaosmod.entity.ai;

import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.MathHelper;

public class EntityAICustomRangedAttack extends EntityAIBase {
    private final EntityMob entity;
    private final double moveSpeedAmp;
    private final int attackCooldown;
    private final float maxAttackDistance;
    private int seeTime;
    private int attackTime = -1;

    public EntityAICustomRangedAttack(EntityMob mob, double moveSpeedAmp, int attackCooldown, float maxDistance) {
        this.entity = mob;
        this.moveSpeedAmp = moveSpeedAmp;
        this.attackCooldown = attackCooldown;
        this.maxAttackDistance = maxDistance * maxDistance;
        this.setMutexBits(3); // movement and look
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase target = this.entity.getAttackTarget();
        return target != null && target.isEntityAlive();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void resetTask() {
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.entity.getAttackTarget();

        if (target == null)
            return;

        double distanceSq = this.entity.getDistanceSq(target);
        boolean canSee = this.entity.getEntitySenses().canSee(target);

        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (distanceSq <= (double) this.maxAttackDistance && this.seeTime >= 20) {
            this.entity.getNavigator().clearPath();
        } else {
            this.entity.getNavigator().tryMoveToEntityLiving(target, this.moveSpeedAmp);
        }

        this.entity.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

        --this.attackTime;

        if (this.attackTime <= 0) {
            if (distanceSq <= (double) this.maxAttackDistance && canSee) {
                this.attackTime = this.attackCooldown;
                
                if (this.entity instanceof EntityMountainGiantBoss) {
                    ((EntityMountainGiantBoss) this.entity).startAttack();
                }

                // Trigger attack animation
                ((IRangedAttackMob) this.entity).setSwingingArms(true);

                // Fire the projectile
                EntityRock rock = new EntityRock(this.entity.world, this.entity);
                double dx = target.posX - this.entity.posX;
                double dy = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - rock.posY;
                double dz = target.posZ - this.entity.posZ;
                float f = MathHelper.sqrt(dx * dx + dz * dz) * 0.2F;

                rock.shoot(dx, dy + f, dz, 1.6F, 8.0F);
                this.entity.world.spawnEntity(rock);
            }
        }
    }
}