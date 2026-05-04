package util.handlers.entity;

import net.chaos.chaosmod.client.renderer.tileentity.AtmTESR;
import net.chaos.chaosmod.client.renderer.tileentity.DrawerTESR;
import net.chaos.chaosmod.client.renderer.tileentity.LanternTESR;
import net.chaos.chaosmod.client.renderer.tileentity.TESRCookieJar;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBeamRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityBossAltarRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TileEntityOxoniumChestRenderer;
import net.chaos.chaosmod.client.renderer.tileentity.TrophyTESR;
import net.chaos.chaosmod.entity.EntityBipedBasic;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.EntityEyeCrystal;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.EntitySphere;
import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.animal.EntityBear;
import net.chaos.chaosmod.entity.animal.EntityBee;
import net.chaos.chaosmod.entity.animal.EntityVulture;
import net.chaos.chaosmod.entity.animal.render.RenderBear;
import net.chaos.chaosmod.entity.animal.render.RenderBee;
import net.chaos.chaosmod.entity.animal.render.RenderVulture;
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
import net.chaos.chaosmod.entity.render.EntitySphereRenderer;
import net.chaos.chaosmod.entity.render.EntitySwordOfWrathRenderer;
import net.chaos.chaosmod.entity.render.EntityVikingRenderer;
import net.chaos.chaosmod.entity.render.LittleGiantRenderer;
import net.chaos.chaosmod.entity.render.RenderChaosSage;
import net.chaos.chaosmod.entity.render.RenderForgeGuardian;
import net.chaos.chaosmod.init.ModFluidBlocks;
import net.chaos.chaosmod.tileentity.TileEntityATM;
import net.chaos.chaosmod.tileentity.TileEntityBeam;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.chaos.chaosmod.tileentity.TileEntityCookieJar;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityLantern;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHandler {

	public static void registerEntityRenderers() {
		registerEntityRenderer(EntityForgeGuardian.class, RenderForgeGuardian::new);
		registerEntityRenderer(EntityRevengeBlazeBoss.class, EntityRevengeBlazeRenderer::new);
		registerEntityRenderer(EntitySmallBlueFireball.class, EntitySmallBlueFireballRenderer::new);
		registerEntityRenderer(EntityRock.class, EntityRockRenderer::new);
		registerEntityRenderer(EntityMenhir.class, EntityMenhirRenderer::new);
		registerEntityRenderer(EntityMountainGiantBoss.class, EntityMountainGiantBossRenderer::new);
		registerEntityRenderer(EntityChaosSage.class, RenderChaosSage::new);
		registerEntityRenderer(EntityViking.class, EntityVikingRenderer::new);
		registerEntityRenderer(EntityPicsou.class, EntityPicsouRenderer::new);
		registerEntityRenderer(LittleGiantEntity.class, LittleGiantRenderer::new);
		registerEntityRenderer(EntityEyeCrystalBoss.class, EntityEyeCrystalBossRenderer::new);
		registerEntityRenderer(EntityEyeCrystal.class, EntityEyeCrystalRenderer::new);
		registerEntityRenderer(ChaosMasterBoss.class, CMRenderer::new);
		registerEntityRenderer(EntityBipedBasic.class, EntityBipedBasicRenderer::new);
		registerEntityRenderer(EntitySphere.class, EntitySphereRenderer::new);
		registerEntityRenderer(EntitySwordOfWrath.class, EntitySwordOfWrathRenderer::new);
		registerEntityRenderer(EntityBear.class, RenderBear::new);
		registerEntityRenderer(EntityVulture.class, RenderVulture::new);
		registerEntityRenderer(EntityBee.class, RenderBee::new);
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityATM.class, new AtmTESR());
	}

	/**
	 * Add fluids here also
	 */
	public static void registerCustomMeshesAndStates() {
		ModFluidBlocks.registerCustomMeshesAndStates();
	}
	
	private static <T extends Entity> void registerEntityRenderer(Class<T> entityClass, IRenderFactory<T> factory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, factory);
	}
}