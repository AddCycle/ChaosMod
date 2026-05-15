package net.chaos.chaosmod.entity;

public interface IBossPhase {
    /**
     * Called when this phase is set to active
     */
    void initPhase();
    
    /**
     * Updates the phase status
     */
    void update();

    /**
     * The difficulty cost of the phase in order to make a balanced fight (even if no luck, not hard patterns always)
     * @return
     */
    int getCost();
//    PhaseList <? extends IPhase > getType();
}