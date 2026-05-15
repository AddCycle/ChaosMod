package net.chaos.chaosmod.entity.boss;

import net.chaos.chaosmod.entity.IBossPhase;
import net.minecraft.entity.Entity;

// TODO : inspired by EnderDragon's PhaseManager
public class AbstractBossPhaseManager<T extends Entity> {
	private final IBossPhase[] phases;
	private final T entity;
	private IBossPhase phase;
	
	public AbstractBossPhaseManager(IBossPhase[] phases, T entity, IBossPhase initialPhase) {
		this.phases = phases;
		this.entity = entity;
		this.phase = initialPhase;
	}
	
	public void setPhase(IBossPhase phase) {
		this.phase = phase;
//		this.entity.getDataManager().set(entity.PHASE, phase.getId());
	}

	public IBossPhase getCurrentPhase() {
		return phase;
	}

//	public <P extends IBossPhase> P getPhase() {
//		
//	}
}