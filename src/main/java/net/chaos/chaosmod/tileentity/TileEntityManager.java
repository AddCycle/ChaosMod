package net.chaos.chaosmod.tileentity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import util.Reference;

public class TileEntityManager {

	public static void registerTileEntities() {
    	GameRegistry.registerTileEntity(TileEntityOxoniumFurnace.class, new ResourceLocation(Reference.MODID, "oxonium_furnace"));
    	GameRegistry.registerTileEntity(TileEntityOxoniumChest.class, new ResourceLocation(Reference.MODID, "oxonium_chest"));
    	GameRegistry.registerTileEntity(TileEntityBossAltar.class, new ResourceLocation(Reference.MODID, "boss_altar"));
    	GameRegistry.registerTileEntity(TileEntityForge.class, new ResourceLocation(Reference.MODID, "forge_interface"));
    	GameRegistry.registerTileEntity(TileEntityLantern.class, new ResourceLocation(Reference.MODID, "lantern"));
    	GameRegistry.registerTileEntity(TileEntityCookieJar.class, new ResourceLocation(Reference.MODID, "cookie_jar"));
    	GameRegistry.registerTileEntity(TileEntityBeam.class, new ResourceLocation(Reference.MODID, "beam_block"));
    	GameRegistry.registerTileEntity(TileEntityTrophyBase.class, new ResourceLocation(Reference.MODID, "trophy_base"));
    	GameRegistry.registerTileEntity(TileEntityChaosGateway.class, new ResourceLocation(Reference.MODID, "chaos_gateway"));
	}

}
