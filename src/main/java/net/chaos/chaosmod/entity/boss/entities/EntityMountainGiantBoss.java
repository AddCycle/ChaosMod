package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMountainGiantBoss extends EntityMob {
    public final BossInfoServer bossInfo;

	public EntityMountainGiantBoss(World worldIn) {
		super(worldIn);
		bossInfo = new BossInfoServer(getDisplayName(), Color.BLUE, Overlay.PROGRESS);
	}

}
