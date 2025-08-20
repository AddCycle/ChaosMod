package proxy;

import net.chaos.chaosmod.client.inventory.render.LayerNecklace;
import net.chaos.chaosmod.client.inventory.render.LayerShield;
import net.chaos.chaosmod.cutscene.CutsceneEvents;
import net.chaos.chaosmod.entity.boss.gui.BossBarRendering;
import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.lore.dialogs.DialogEventHandler;
import net.chaos.chaosmod.minimap.MinimapEventHandler;
import net.chaos.chaosmod.minimap.Renderer;
import net.chaos.chaosmod.world.events.PlayerFireRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import util.Reference;
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
        ModKeybinds.init();
        MinecraftForge.EVENT_BUS.register(new PlayerFireRenderHandler());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
        MinecraftForge.EVENT_BUS.register(new MinimapEventHandler());
        Renderer.init();
        MinecraftForge.EVENT_BUS.register(new DialogEventHandler());
        MinecraftForge.EVENT_BUS.register(new CutsceneEvents());
		// OxoniumFurnace to suppress nametag
        RenderHandler.bindTESRs();
		// MinecraftForge.EVENT_BUS.register(new PlayerRenderManager());
		MinecraftForge.EVENT_BUS.register(new BossBarRendering());
		// ################################# NECKLACE RENDERING #############################################

	    // Add the layer to both Steve and Alex models
		Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(renderer -> {
		    renderer.addLayer(new LayerNecklace(renderer));
		    renderer.addLayer(new LayerShield(renderer));
		});
		
		// ################################# END NECKLACE RENDERING ############################################
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
}
