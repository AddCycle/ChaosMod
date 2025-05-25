package net.chaos.chaosmod.init;

import net.chaos.chaosmod.world.structures.DimensionProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	public static DimensionType CUSTOM;
	public static int customId;
	
	public static void init() {
		registerDimensionTypes();
	}
	
	public static void registerDimensionTypes() {
		customId = DimensionManager.getNextFreeDimId();
		CUSTOM = DimensionType.register("custom_dim", "_dim", customId, DimensionProvider.class, false);
		DimensionManager.registerDimension(customId, CUSTOM);
	}
}
