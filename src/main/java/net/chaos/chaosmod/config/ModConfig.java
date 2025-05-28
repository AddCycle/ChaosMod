package net.chaos.chaosmod.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ModConfig {
	public static boolean isMinimapEnabled = false;
	public static boolean displayOverlay = false;
	public static boolean displayArrow = false;
	public static boolean displayCoords = false;
	public static int minimapSize = 1;
	public static int pixelSize = 1;

	private static Configuration config;

	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfig();
		}
	}

	public static void loadConfig() {
		
		isMinimapEnabled = config.getBoolean("Enable Minimap", Configuration.CATEGORY_CLIENT, false, "Enable or disable the minimap.");
		minimapSize = config.getInt("Minimap Size", Configuration.CATEGORY_CLIENT, 100, 1, 500, "Change the minimap squared size");
		pixelSize = config.getInt("Pixel Size", Configuration.CATEGORY_CLIENT, 4, 1, 20, "Change the minimap reach");
		displayOverlay = config.getBoolean("Enable Outline", Configuration.CATEGORY_CLIENT, false, "Enable or disable the minimap outline (style).");
		displayArrow = config.getBoolean("Enable Player Arrow", Configuration.CATEGORY_CLIENT, false, "Enable or disable player arrow pos");
		displayCoords = config.getBoolean("Enable Coordinates", Configuration.CATEGORY_CLIENT, false, "Print or hide coordinates");

		if (config.hasChanged()) {
			config.save();
		}
	}
	
	public static Configuration getConfig() {
	    return config;
	}
}