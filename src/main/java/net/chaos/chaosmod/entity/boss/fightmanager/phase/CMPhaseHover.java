package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.minecraft.entity.boss.dragon.phase.PhaseBase;
import net.minecraft.entity.boss.dragon.phase.PhaseHover;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.util.math.Vec3d;

public class CMPhaseHover extends CMPhaseBase {
    private Vec3d targetLocation;

    public CMPhaseHover(ChaosMasterBoss dragonIn)
    {
        super(dragonIn);
    }

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    public void doLocalUpdate()
    {
        if (this.targetLocation == null)
        {
            this.targetLocation = new Vec3d(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
        }
    }

    public boolean getIsStationary()
    {
        return true;
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.targetLocation = null;
    }

    /**
     * Returns the maximum amount dragon may rise or fall during this phase
     */
    public float getMaxRiseOrFall()
    {
        return 1.0F;
    }

    /**
     * Returns the location the dragon is flying toward
     */
    @Nullable
    public Vec3d getTargetLocation()
    {
        return this.targetLocation;
    }

    public CMPhaseList<CMPhaseHover> getType()
    {
        return CMPhaseList.HOVER;
    }
}
