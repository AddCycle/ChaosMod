package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.minecraft.entity.boss.dragon.phase.PhaseChargingPlayer;
import net.minecraft.entity.boss.dragon.phase.PhaseList;
import net.minecraft.util.math.Vec3d;

public class CMPhaseChargingPlayer extends CMPhaseBase {
    private static final Logger LOGGER = LogManager.getLogger();
    private Vec3d targetLocation;
    private int timeSinceCharge;

    public CMPhaseChargingPlayer(ChaosMasterBoss dragonIn)
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
            LOGGER.warn("Aborting charge player as no target was set.");
            this.dragon.getPhaseManager().setPhase(CMPhaseList.HOLDING_PATTERN);
        }
        else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10)
        {
            this.dragon.getPhaseManager().setPhase(CMPhaseList.HOLDING_PATTERN);
        }
        else
        {
            double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);

            if (d0 < 100.0D || d0 > 22500.0D || this.dragon.collidedHorizontally || this.dragon.collidedVertically)
            {
                ++this.timeSinceCharge;
            }
        }
    }

    /**
     * Called when this phase is set to active
     */
    public void initPhase()
    {
        this.targetLocation = null;
        this.timeSinceCharge = 0;
    }

    public void setTarget(Vec3d p_188668_1_)
    {
        this.targetLocation = p_188668_1_;
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

    public CMPhaseList<CMPhaseChargingPlayer> getType()
    {
        return CMPhaseList.CHARGING_PLAYER;
    }
}
