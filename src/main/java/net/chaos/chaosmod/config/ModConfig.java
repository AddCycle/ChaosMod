package net.chaos.chaosmod.config;

import net.minecraftforge.common.config.Config;
import util.Reference;

// Not working must fix
@Config(modid = Reference.MODID)
public class ModConfig {
	
    public static final Client CLIENT = new Client();

    public static final Server SERVER = new Server();

	public static class Client {

		@Config.Name("Enable Minimap")
		@Config.Comment("Enable or disable the minimap.")
		public boolean isMinimapEnabled = false;

		@Config.Name("Display Overlay")
		@Config.Comment("Enable or disable the minimap outline (style).")
		public boolean displayOverlay = false;

		@Config.Name("Display Player Arrow")
		@Config.Comment("Enable or disable player arrow pos")
		public boolean displayArrow = false;

		@Config.Name("Display Coordinates")
		@Config.Comment("Print or hide coordinates")
		public boolean displayCoords = false;

		@Config.Name("Minimap Size")
		@Config.Comment("Change the minimap squared size")
		@Config.RangeInt(min = 50, max = 200)
		public int minimapSize = 50;

		@Config.Name("Pixel Size")
		@Config.Comment("Change the minimap reach")
		@Config.RangeInt(min = 1, max = 4)
		public int pixelSize = 1;

		@Config.Name("Enable Custom Block Outline Color")
		@Config.Comment("Enable or disable Custom Block outline color")
		public boolean isBlockOutlineColorEnabled = false;

		@Config.Name("Block Outline Color")
		@Config.Comment("Choose between 0 and 16,777,215")
		@Config.RangeInt(min = 0x000000, max = 0xffffff)
		public int block_outline_color = 0x000000;

		@Config.Name("Additional Block/Entity infos")
		@Config.Comment("Display the raytraced object name & mod")
		public boolean areAdditionalInfosEnabled = true;
		
		@Config.Name("Additional Block/Entity infos background")
		@Config.Comment("Maybe you can see read it more clearly")
		public boolean isAdditionalInfosBackgroundEnabled = false;
	}

	public static class Server {
		@Config.Name("Viking Boat Spawn Rate")
		@Config.Comment("Choose between 10 to 40 (which is too much)")
		@Config.RangeInt(min = 10, max = 40)
		public int boat_spawn_rate = 10;
	}
}