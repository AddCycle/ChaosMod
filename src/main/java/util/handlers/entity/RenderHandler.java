package util.handlers.entity;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.renderer.tileentity.DrawerTESR;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBeamRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBossAltarRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TrophyTESR;
import net.chaos.chaosmod.entity.EntityBipedBasic;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.EntityEyeCrystal;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystalBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.boss.renderer.CMRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityEyeCrystalBossRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityMountainGiantBossRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityRevengeBlazeRenderer;
import net.chaos.chaosmod.entity.projectile.EntityMenhir;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.chaos.chaosmod.entity.projectile.render.EntityMenhirRenderer;
import net.chaos.chaosmod.entity.projectile.render.EntityRockRenderer;
import net.chaos.chaosmod.entity.projectile.render.EntitySmallBlueFireballRenderer;
import net.chaos.chaosmod.entity.render.EntityBipedBasicRenderer;
import net.chaos.chaosmod.entity.render.EntityEyeCrystalRenderer;
import net.chaos.chaosmod.entity.render.EntityPicsouRenderer;
import net.chaos.chaosmod.entity.render.EntityVikingRenderer;
import net.chaos.chaosmod.entity.render.LittleGiantRenderer;
import net.chaos.chaosmod.entity.render.RenderChaosSage;
import net.chaos.chaosmod.entity.render.RenderForgeGuardian;
import net.chaos.chaosmod.init.ModFluidBlocks;
import net.chaos.chaosmod.tileentity.LanternTESR;
import net.chaos.chaosmod.tileentity.TESRCookieJar;
import net.chaos.chaosmod.tileentity.TileEntityBeam;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityCookieJar;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {

	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(EntityForgeGuardian.class, new IRenderFactory<EntityForgeGuardian>() {

			@Override
			public Render<? super EntityForgeGuardian> createRenderFor(RenderManager manager) {
				return new RenderForgeGuardian(manager);
			}

		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityRevengeBlazeBoss.class, new IRenderFactory<EntityRevengeBlazeBoss>() {
			@Override
			public Render<? super EntityRevengeBlazeBoss> createRenderFor(RenderManager manager) {
				return new EntityRevengeBlazeRenderer(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySmallBlueFireball.class, new IRenderFactory<EntitySmallBlueFireball>() {

			@Override
			public Render<? super EntitySmallBlueFireball> createRenderFor(RenderManager manager) {
				return new EntitySmallBlueFireballRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityRock.class, new IRenderFactory<EntityRock>() {

			@Override
			public Render<? super EntityRock> createRenderFor(RenderManager manager) {
				return new EntityRockRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityMenhir.class, new IRenderFactory<EntityMenhir>() {

			@Override
			public Render<? super EntityMenhir> createRenderFor(RenderManager manager) {
				return new EntityMenhirRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityMountainGiantBoss.class, new IRenderFactory<EntityMountainGiantBoss>() {

			@Override
			public Render<? super EntityMountainGiantBoss> createRenderFor(RenderManager manager) {
				return new EntityMountainGiantBossRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityChaosSage.class, new IRenderFactory<EntityChaosSage>() {

			@Override
			public Render<? super EntityChaosSage> createRenderFor(RenderManager manager) {
				return new RenderChaosSage(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityViking.class, new IRenderFactory<EntityViking>() {

			@Override
			public Render<? super EntityViking> createRenderFor(RenderManager manager) {
				return new EntityVikingRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityPicsou.class, new IRenderFactory<EntityPicsou>() {

			@Override
			public Render<? super EntityPicsou> createRenderFor(RenderManager manager) {
				return new EntityPicsouRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(LittleGiantEntity.class, new IRenderFactory<LittleGiantEntity>() {

			@Override
			public Render<? super LittleGiantEntity> createRenderFor(RenderManager manager) {
				return new LittleGiantRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityEyeCrystalBoss.class, new IRenderFactory<EntityEyeCrystalBoss>() {

			@Override
			public Render<? super EntityEyeCrystalBoss> createRenderFor(RenderManager manager) {
				return new EntityEyeCrystalBossRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityEyeCrystal.class, new IRenderFactory<EntityEyeCrystal>() {

			@Override
			public Render<? super EntityEyeCrystal> createRenderFor(RenderManager manager) {
				return new EntityEyeCrystalRenderer(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(ChaosMasterBoss.class, new IRenderFactory<ChaosMasterBoss>() {

			@Override
			public Render<? super ChaosMasterBoss> createRenderFor(RenderManager manager) {
				return new CMRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityBipedBasic.class, new IRenderFactory<EntityBipedBasic>() {

			@Override
			public RenderLiving<? super EntityBipedBasic> createRenderFor(RenderManager manager) {
				return new EntityBipedBasicRenderer(manager);
			}
			
		});
	}
	
	public static void registerCustomMeshesAndStates() {
		registerFluidRendering(ModFluidBlocks.FERTILIZED_WATER_BLOCK);
	}
	
	public static void registerFluidRendering(Block fluidBlock) {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(fluidBlock), new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(fluidBlock.getRegistryName(), "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				Main.getLogger().info("Registering state mapper for : {}", fluidBlock.getRegistryName());
				return new ModelResourceLocation(fluidBlock.getRegistryName(), "fluid");
			}
		});
	}

	public static void bindTESRs() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOxoniumFurnace.class, new TileEntitySpecialRenderer<TileEntity>() {
			@Override
			protected void drawNameplate(TileEntity te, String str, double x, double y, double z, int maxDistance) {}
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCookieJar.class, new TESRCookieJar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBeam.class, new TileEntityBeamRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrophyBase.class, new TrophyTESR());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDrawer.class, new DrawerTESR());
	}

}
