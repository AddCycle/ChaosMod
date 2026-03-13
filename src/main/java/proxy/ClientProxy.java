package proxy;

import net.chaos.chaosmod.blocks.complex.mimic.MimicStartupClient;
import net.chaos.chaosmod.client.inventory.render.LayerNecklace;
import net.chaos.chaosmod.client.inventory.render.LayerShield;
import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.minimap.Renderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import util.Reference;
import util.handlers.entity.RenderHandler;

public class ClientProxy extends CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void registerVariantRenderer(Item item, int meta, String name, String variant) {
		ResourceLocation rl = new ResourceLocation(Reference.MODID, name);
	    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(rl, variant));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
        RenderHandler.registerEntityRenders();
        ModKeybinds.init();
        MimicStartupClient.preInitClientOnly();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
        Renderer.init();
        RenderHandler.bindTESRs();
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