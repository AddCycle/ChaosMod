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
	public static final String JEI = "jei";
	public static final String MATHSMOD = "mathsmod";
	public static final String PATCHOULI = "patchouli";
	public static final String BIOMESOPLENTY = "biomesoplenty";
    
    // GUI ids
    public static final int GUI_GUIDE_ID = 0;
    public static final int GUI_FURNACE_ID = 1;
    public static final int GUI_CREDITS_ID = 2;
    public static final int GUI_FORGE_ID = 3;
    public static final int GUI_BACKPACK_ID = 4;
    public static final int GUI_DOCS_ID = 5;
    // 6 is for the extended player gui
    public static final int GUI_ACCESSORY_ID = 6;
    public static final int GUI_TROPHY = 7;
	public static final int GUI_ATM_ID = 8;
	public static final int GUI_MARKET_ID = 9;

    // Entities ids
    private static int entityId = 120;
    public static final int ENTITY_FORGE_GUARDIAN = entityId++;
	public static final int REVENGE_BLAZE_BOSS = entityId++;
	public static final int SMALL_BLUE_FIREBALL = entityId++;
	public static final int MOUNTAIN_GIANT_BOSS = entityId++;
	public static final int ENTITY_ROCK = entityId++;
	public static final int CHAOS_SAGE = entityId++;
	public static final int ENTITY_MENHIR = entityId++;
	public static final int ENTITY_VIKING = entityId++;
	public static final int ENTITY_PICSOU = entityId++;
	public static final int ENTITY_GIANTS = entityId++;
	public static final int EYE_OF_TRUTH = entityId++;
	public static final int EYE_CRYSTAL = entityId++;
	public static final int LIGHT_ENTITY = entityId++;
	public static final int CHAOS_MASTER = entityId++;
	public static final int ENTITY_BIPED_BASIC = entityId++;
	public static final int ENTITY_SPHERE = entityId++;
}