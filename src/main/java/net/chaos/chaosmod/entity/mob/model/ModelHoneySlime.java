package net.chaos.chaosmod.entity.mob.model;

import net.minecraft.client.model.ModelSlime;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHoneySlime extends ModelSlime {

	// vanilla size is 16
	public ModelHoneySlime(int size) {
		super(size);
	}
}