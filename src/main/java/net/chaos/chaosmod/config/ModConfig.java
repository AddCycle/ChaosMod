package net.chaos.chaosmod.config;

import java.io.File;

import net.chaos.chaosmod.minimap.Renderer;
//import net.chaos.chaosmod.minimap.Renderer;
import net.minecraftforge.common.config.Configuration;

public class ModConfig {
	public static boolean isMinimapEnabled = false;
	public static boolean displayOverlay = false;
	public static boolean displayArrow = false;
	public static boolean displayCoords = false;
	public static int minimapSize = 1;
	public static int pixelSize = 1;
	public static boolean isBlockOutlineColorEnabled = false;
	public static int block_outline_color = 0x000000;
	public static int boat_spawn_rate = 10;

	private static Configuration config;

	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfig();
		}
	}

	public static void loadConfig() {
		isMinimapEnabled = config.getBoolean("Enable Minimap", Configuration.CATEGORY_CLIENT, false, "Enable or disable the minimap.");
		minimapSize = config.getInt("Minimap Size", Configuration.CATEGORY_CLIENT, 50, 50, 200, "Change the minimap squared size");
		pixelSize = config.getInt("Pixel Size", Configuration.CATEGORY_CLIENT, 1, 1, 4, "Change the minimap reach");
		displayOverlay = config.getBoolean("Enable Outline", Configuration.CATEGORY_CLIENT, false, "Enable or disable the minimap outline (style).");
		displayArrow = config.getBoolean("Enable Player Arrow", Configuration.CATEGORY_CLIENT, false, "Enable or disable player arrow pos");
		displayCoords = config.getBoolean("Enable Coordinates", Configuration.CATEGORY_CLIENT, false, "Print or hide coordinates");
		isBlockOutlineColorEnabled = config.getBoolean("Enable Custom Block Outline Color", Configuration.CATEGORY_CLIENT, false, "Enable or disable Custom Block outline color");
		block_outline_color = config.getInt("Block Outline Color", Configuration.CATEGORY_CLIENT, 0x000000, 0x000000, 0xffffff, "Choose between 0 and 16,777,215");
		boat_spawn_rate = config.getInt("Viking Boat Spawn Rate", Configuration.CATEGORY_GENERAL, 10, 10, 40, "Choose between 10 to 40 (which is too much)");

		if (config.hasChanged()) {
			Renderer.clearMinimap();
			config.save();
		}
	}
	
	public static Configuration getConfig() {
	    return config;
	}
}