package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.minecraft.entity.boss.dragon.phase.PhaseDying;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class CMPhaseDying extends CMPhaseBase {
    private Vec3d targetLocation;
    private int time;

    public CMPhaseDying(ChaosMasterBoss dragonIn)
    {
        super(dragonIn);
    }

    /**
     * Generates particle effects appropriate to the phase (or sometimes sounds).
     * Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
     */
    public void doClientRenderEffects()
    {
        if (this.time++ % 10 == 0)
        {
            float f = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.dragon.getRNG().nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
            this.dragon.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.dragon.posX + (double)f, this.dragon.posY + 2.0D + (double)f1, this.dragon.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        ++this.time;

        if (this.targetLocation == null)
        {
            BlockPos blockpos = this.dragon.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION);
            this.targetLocation = new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
        }

        double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);

        if (d0 >= 100.0D && d0 <= 22500.0D && !this.dragon.collidedHorizontally && !this.dragon.collidedVertically)
        {
            this.dragon.setHealth(1.0F);
        }
        else
        {
            this.dragon.setHealth(0.0F);
        }
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.targetLocation = null;
        this.time = 0;
    }

    /**
     * Returns the maximum amount dragon may rise or fall during this phase
     */
    public float getMaxRiseOrFall()
    {
        return 3.0F;
    }

    /**
     * Returns the location the dragon is flying toward
     */
    @Nullable
    public Vec3d getTargetLocation()
    {
        return this.targetLocation;
    }

    public CMPhaseList<CMPhaseDying> getType()
    {
        return CMPhaseList.DYING;
    }
}
