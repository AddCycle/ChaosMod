package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import javax.annotation.Nullable;

import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface ICMPhase {
    boolean getIsStationary();

    /**
     * Generates particle effects appropriate to the phase (or sometimes sounds).
     * Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
     */
    void doClientRenderEffects();

    /**
     * Gives the phase a chance to update its status.
     * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
     */
    void doLocalUpdate();

    void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc, @Nullable EntityPlayer plyr);

    /**
     * Called when this phase is set to active
     */
    void initPhase();

    void removeAreaEffect();

    /**
     * Returns the maximum amount dragon may rise or fall during this phase
     */
    float getMaxRiseOrFall();

    float getYawFactor();

    CMPhaseList <? extends ICMPhase > getType();

    /**
     * Returns the location the dragon is flying toward
     */
    @Nullable
    Vec3d getTargetLocation();

    /**
     * Normally, just returns damage. If dragon is sitting and src is an arrow, arrow is enflamed and zero damage
     * returned.
     */
    float getAdjustedDamage(MultiPartEntityPart pt, DamageSource src, float damage);
}
