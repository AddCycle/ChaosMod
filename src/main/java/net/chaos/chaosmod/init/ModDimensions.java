package net.chaos.chaosmod.init;

import net.chaos.chaosmod.world.gen.experimental.ExperimentalWorldProvider;
import net.chaos.chaosmod.world.structures.DimensionProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {
	public static DimensionType EXPERIMENTAL;
	public static DimensionType CUSTOM;
	
	public static void init() {
		registerDimensionTypes();
	}
	
	public static void registerDimensionTypes() {
		CUSTOM = registerDimension("chaosland_dim", "_dim", DimensionProvider.class, false);
		EXPERIMENTAL = registerDimension("experimental_dim", "_dim", ExperimentalWorldProvider.class, false);
	}
	
	private static DimensionType registerDimension(String name, String suffix, Class<? extends WorldProvider> clazz, boolean keepLoaded) {
		int id = DimensionManager.getNextFreeDimId();
		DimensionType type = DimensionType.register(name, suffix, id, clazz, keepLoaded);
		DimensionManager.registerDimension(id, type);
		return type;
	}
}