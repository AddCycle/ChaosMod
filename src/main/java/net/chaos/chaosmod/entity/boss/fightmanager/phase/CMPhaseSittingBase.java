package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;

public abstract class CMPhaseSittingBase extends CMPhaseBase
{
    public CMPhaseSittingBase(ChaosMasterBoss p_i46794_1_)
    {
        super(p_i46794_1_);
    }

    public boolean getIsStationary()
    {
        return true;
    }

    /**
     * Normally, just returns damage. If dragon is sitting and src is an arrow, arrow is enflamed and zero damage
     * returned.
     */
    public float getAdjustedDamage(MultiPartEntityPart pt, DamageSource src, float damage)
    {
        if (src.getImmediateSource() instanceof EntityArrow)
        {
            src.getImmediateSource().setFire(1);
            return 0.0F;
        }
        else
        {
            return super.getAdjustedDamage(pt, src, damage);
        }
    }
}
