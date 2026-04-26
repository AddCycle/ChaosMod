package net.chaos.chaosmod.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import util.Reference;

public class TileEntityManager {

	public static void registerTileEntities() {
		registerTE(TileEntityOxoniumFurnace.class, "oxonium_furnace");
		registerTE(TileEntityOxoniumChest.class, "oxonium_chest");
		registerTE(TileEntityBossAltar.class, "boss_altar");
		registerTE(TileEntityForge.class, "forge_interface");
		registerTE(TileEntityLantern.class, "lantern");
		registerTE(TileEntityCookieJar.class, "cookie_jar");
		registerTE(TileEntityBeam.class, "beam_block");
		registerTE(TileEntityTrophyBase.class, "trophy_base");
		registerTE(TileEntityChaosGateway.class, "chaos_gateway");
		registerTE(TileEntityDrawer.class, "drawer");
		registerTE(TileEntityATM.class, "atm_machine");
		registerTE(TileEntityJigsaw.class, "jigsaw_block");
	}
	
	private static void registerTE(Class<? extends TileEntity> clazz, String id) {
    	GameRegistry.registerTileEntity(clazz, new ResourceLocation(Reference.MODID, id));
	}
}