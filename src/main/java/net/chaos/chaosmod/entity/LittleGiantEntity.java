package net.chaos.chaosmod.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class LittleGiantEntity extends EntityLiving{

	public LittleGiantEntity(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 1.5f);
	}

}
