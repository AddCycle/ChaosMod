package proxy;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.commands.GuideCommand;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.handlers.RegistryHandler;

// Adds a simple mapping from Item + metadata to the model variant. (en gros il enregistre les objets dans la structure du loader pour afficher correctement les objets)
public class ClientProxy extends CommonProxy {

	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		// OxoniumFurnace to suppress nametag
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOxoniumFurnace.class, new TileEntitySpecialRenderer<TileEntity>() {
			/*@Override
			protected void drawNameplate(TileEntity te, String str, double x, double y, double z, int maxDistance) {
				return;
			}*/
			
			@Override
			public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage,
					float alpha) {
				return;
				// super.render(te, x, y, z, partialTicks, destroyStage, alpha);
				/*OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 1.0F, 1.0F);
				GlStateManager.enableLighting();
				GlStateManager.pushMatrix();
				GlStateManager.rotate(te.getWorld().getTotalWorldTime() + partialTicks, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ());
				GlStateManager.popMatrix();*/
			}
			
			/*public void renderAtCentre(TileEntity te, float partialTicks, int destroyStage, float alpha) {




				GlStateManager.translate(0, 3, 0);

				GlStateManager.scale(3, 3, 3);

				GlStateManager.enableBlend();

				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

				GlStateManager.disableBlend();
				GlStateManager.popMatrix();
			}*/
				});

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOxoniumChest.class, new TileEntityOxoniumChestRenderer<TileEntityOxoniumChest>());
		// MinecraftForge.EVENT_BUS.register(new PlayerRenderManager());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		RegistryHandler.renderItems();
	}
	
}
