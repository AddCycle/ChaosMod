package proxy;

import java.util.Map;

import net.chaos.chaosmod.client.inventory.render.LayerNecklace;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBossAltarRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.entity.boss.gui.BossBarRendering;
import net.chaos.chaosmod.tileentity.LanternTESR;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import util.Reference;
import util.handlers.RegistryHandler;
import util.handlers.entity.RenderHandler;

// Adds a simple mapping from Item + metadata to the model variant. (en gros il enregistre les objets dans la structure du loader pour afficher correctement les objets)
public class ClientProxy extends CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void registerVariantRenderer(Item item, int meta, String name, String variant) {
	    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, name), variant));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
        RenderHandler.registerEntityRenders();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		// MinecraftForge.EVENT_BUS.register(ClientMessageHandler.class);
		// OxoniumFurnace to suppress nametag
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOxoniumFurnace.class, new TileEntitySpecialRenderer<TileEntity>() {

			@Override
			protected void drawNameplate(TileEntity te, String str, double x, double y, double z, int maxDistance) {
				return;
			}
				
		});

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOxoniumChest.class, new TileEntityOxoniumChestRenderer<TileEntityOxoniumChest>());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBossAltar.class, new TileEntityBossAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntitySpecialRenderer<TileEntity>() {
			@Override
			protected void drawNameplate(TileEntity te, String str, double x, double y, double z, int maxDistance) {
				return;
			}
		});
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLantern.class, new LanternTESR());
		// MinecraftForge.EVENT_BUS.register(new PlayerRenderManager());
		MinecraftForge.EVENT_BUS.register(new BossBarRendering());
		// ################################# NECKLACE RENDERING #############################################
		/*Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

	    // Add the layer to both Steve and Alex models
	    skinMap.get("default").addLayer(new LayerNecklace(skinMap.get("default")));
	    skinMap.get("slim").addLayer(new LayerNecklace(skinMap.get("slim")));*/
		Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(renderer -> {
		    renderer.addLayer(new LayerNecklace(renderer));
		});
		// ################################# END NECKLACE RENDERING #############################################
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		RegistryHandler.renderItems();
	}
	
}
