package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseChargingPlayer;
import net.minecraft.entity.boss.dragon.phase.PhaseDying;
import net.minecraft.entity.boss.dragon.phase.PhaseHoldingPattern;
import net.minecraft.entity.boss.dragon.phase.PhaseHover;
import net.minecraft.entity.boss.dragon.phase.PhaseLanding;
import net.minecraft.entity.boss.dragon.phase.PhaseLandingApproach;
import net.minecraft.entity.boss.dragon.phase.PhaseSittingAttacking;
import net.minecraft.entity.boss.dragon.phase.PhaseSittingFlaming;
import net.minecraft.entity.boss.dragon.phase.PhaseSittingScanning;
import net.minecraft.entity.boss.dragon.phase.PhaseStrafePlayer;
import net.minecraft.entity.boss.dragon.phase.PhaseTakeoff;

public class CMPhaseList<T extends ICMPhase>
{
    private static CMPhaseList<?>[] phases = new CMPhaseList[0];
    public static final CMPhaseList<CMPhaseHoldingPattern> HOLDING_PATTERN = create(CMPhaseHoldingPattern.class, "HoldingPattern");
    public static final CMPhaseList<CMPhaseStrafePlayer> STRAFE_PLAYER = create(CMPhaseStrafePlayer.class, "StrafePlayer");
    public static final CMPhaseList<CMPhaseLandingApproach> LANDING_APPROACH = create(CMPhaseLandingApproach.class, "LandingApproach");
    public static final CMPhaseList<CMPhaseLanding> LANDING = create(CMPhaseLanding.class, "Landing");
    public static final CMPhaseList<CMPhaseTakeoff> TAKEOFF = create(CMPhaseTakeoff.class, "Takeoff");
    public static final CMPhaseList<CMPhaseSittingFlaming> SITTING_FLAMING = create(CMPhaseSittingFlaming.class, "SittingFlaming");
    public static final CMPhaseList<CMPhaseSittingScanning> SITTING_SCANNING = create(CMPhaseSittingScanning.class, "SittingScanning");
    public static final CMPhaseList<CMPhaseSittingAttacking> SITTING_ATTACKING = create(CMPhaseSittingAttacking.class, "SittingAttacking");
    public static final CMPhaseList<CMPhaseChargingPlayer> CHARGING_PLAYER = create(CMPhaseChargingPlayer.class, "ChargingPlayer");
    public static final CMPhaseList<CMPhaseDying> DYING = create(CMPhaseDying.class, "Dying");
    public static final CMPhaseList<CMPhaseHover> HOVER = create(CMPhaseHover.class, "Hover");
    private final Class <? extends ICMPhase > clazz;
    private final int id;
    private final String name;

    private CMPhaseList(int idIn, Class <? extends ICMPhase > clazzIn, String nameIn)
    {
        this.id = idIn;
        this.clazz = clazzIn;
        this.name = nameIn;
    }

    public ICMPhase createPhase(ChaosMasterBoss dragon)
    {
        try
        {
            Constructor <? extends ICMPhase > constructor = this.getConstructor();
            return constructor.newInstance(dragon);
        }
        catch (Exception exception)
        {
            throw new Error(exception);
        }
    }

    protected Constructor <? extends ICMPhase > getConstructor() throws NoSuchMethodException
    {
        return this.clazz.getConstructor(ChaosMasterBoss.class);
    }

    public int getId()
    {
        return this.id;
    }

    public String toString()
    {
        return this.name + " (#" + this.id + ")";
    }

    /**
     * Gets a phase by its ID. If the phase is out of bounds (negative or beyond the end of the phase array), returns
     * {@link #HOLDING_PATTERN}.
     */
    public static CMPhaseList<?> getById(int idIn)
    {
        return idIn >= 0 && idIn < phases.length ? phases[idIn] : HOLDING_PATTERN;
    }

    public static int getTotalPhases()
    {
        return phases.length;
    }

    private static <T extends ICMPhase> CMPhaseList<T> create(Class<T> phaseIn, String nameIn)
    {
        CMPhaseList<T> phaselist = new CMPhaseList<T>(phases.length, phaseIn, nameIn);
        phases = (CMPhaseList[])Arrays.copyOf(phases, phases.length + 1);
        phases[phaselist.getId()] = phaselist;
        return phaselist;
    }
}