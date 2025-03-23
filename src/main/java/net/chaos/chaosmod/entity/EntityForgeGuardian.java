package net.chaos.chaosmod.entity;

import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.world.World;

public class EntityForgeGuardian extends EntityIronGolem {
	public World world;

	public EntityForgeGuardian(World worldIn) {
		super(worldIn);
		this.world = worldIn;
		this.noClip = true;
	}
}
