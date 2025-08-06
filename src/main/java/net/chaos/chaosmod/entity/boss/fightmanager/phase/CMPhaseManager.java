package net.chaos.chaosmod.entity.boss.fightmanager.phase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;

public class CMPhaseManager {
	private static final Logger LOGGER = LogManager.getLogger();
    private final ChaosMasterBoss dragon;
    private final ICMPhase[] phases = new ICMPhase[CMPhaseList.getTotalPhases()];
    private ICMPhase phase;

    public CMPhaseManager(ChaosMasterBoss dragonIn)
    {
        this.dragon = dragonIn;
        this.setPhase(CMPhaseList.HOVER);
    }

    public void setPhase(CMPhaseList<?> phaseIn)
    {
        if (this.phase == null || phaseIn != this.phase.getType()) // FIX : phases were unchecked
        {
            if (this.phase != null)
            {
            	Main.getLogger().info("Current phase chaosmaster dragon : {}", this.phase.getType());
                this.phase.removeAreaEffect();
            }

            this.phase = this.getPhase(phaseIn);

            if (!this.dragon.world.isRemote)
            {
                this.dragon.getDataManager().set(ChaosMasterBoss.PHASE, Integer.valueOf(phaseIn.getId()));
            }

            LOGGER.debug("Dragon is now in phase {} on the {}", phaseIn, this.dragon.world.isRemote ? "client" : "server");
            this.phase.initPhase();
        }
    }

    public ICMPhase getCurrentPhase()
    {
        return this.phase;
    }

    public <T extends ICMPhase> T getPhase(CMPhaseList<T> phaseIn)
    {
        int i = phaseIn.getId();

        if (this.phases[i] == null)
        {
            this.phases[i] = phaseIn.createPhase(this.dragon);
        }

        return (T)this.phases[i];
    }
}
