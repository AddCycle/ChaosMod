package net.chaos.chaosmod.potion;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectVikingFriends extends PotionEffect {

	public PotionEffectVikingFriends(Potion potionIn) {
		super(potionIn);
	}
	
	public PotionEffectVikingFriends(Potion potionIn, int durationIn)
    {
        super(potionIn, durationIn, 0);
    }

    public PotionEffectVikingFriends(Potion potionIn, int durationIn, int amplifierIn)
    {
        super(potionIn, durationIn, amplifierIn, false, true);
    }

}
