package util;

// All the constants describing the project
public class Reference {
	public static final String MODID = "chaosmod";
	public static final String PREFIX = "chaosmod:";
    public static final String NAME = "ChaosMod";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String CLIENT_PROXY_CLASS = "proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "proxy.CommonProxy";
    public static final String GUI_FACTORY_CLASS = "net.chaos.chaosmod.config.ModGuiFactory";
    public static final String OPTIONAL_DEPENDENCIES = "after:jei;after:patchouli;after:mathsmod";
    
    // GUI ids
    public static final int GUI_GUIDE_ID = 0;
    public static final int GUI_FURNACE_ID = 1;
    public static final int GUI_CREDITS_ID = 2;
    public static final int GUI_FORGE_ID = 3;
    public static final int GUI_BACKPACK_ID = 4;
    public static final int GUI_DOCS_ID = 5;
    public static final int GUI_VIKING_ID = 6;
    // 6 is for the extended player gui
    public static final int GUI_TROPHY = 7;

    // Entities ids
    private static int id = 120;
    public static final int ENTITY_FORGE_GUARDIAN = id++;
	public static final int REVENGE_BLAZE_BOSS = id++;
	public static final int SMALL_BLUE_FIREBALL = id++;
	public static final int MOUNTAIN_GIANT_BOSS = id++;
	public static final int ENTITY_ROCK = id++;
	public static final int CHAOS_SAGE = id++;
	public static final int ENTITY_MENHIR = id++;
	public static final int ENTITY_VIKING = id++;
	public static final int ENTITY_PICSOU = id++;
	public static final int ENTITY_GIANTS = id++;
	public static final int EYE_OF_TRUTH = id++;
	public static final int EYE_CRYSTAL = id++;
	public static final int LIGHT_ENTITY = id++;
	public static final int CHAOS_MASTER = id++;
	public static final int ENTITY_BIPED_BASIC = id++;
}