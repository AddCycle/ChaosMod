package util.handlers.entity;

import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.EntityFakeLight;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.boss.entities.ChaosMasterBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystal;
import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.boss.renderer.CMRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityEyeCrystalRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityMountainGiantBossRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityRevengeBlazeRenderer;
import net.chaos.chaosmod.entity.projectile.EntityMenhir;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.chaos.chaosmod.entity.projectile.render.EntityMenhirRenderer;
import net.chaos.chaosmod.entity.projectile.render.EntityRockRenderer;
import net.chaos.chaosmod.entity.projectile.render.EntitySmallBlueFireballRenderer;
import net.chaos.chaosmod.entity.render.EntityPicsouRenderer;
import net.chaos.chaosmod.entity.render.EntityVikingRenderer;
import net.chaos.chaosmod.entity.render.LittleGiantRenderer;
import net.chaos.chaosmod.entity.render.RenderChaosSage;
import net.chaos.chaosmod.entity.render.RenderForgeGuardian;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
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

		RenderingRegistry.registerEntityRenderingHandler(EntityEyeCrystal.class, new IRenderFactory<EntityEyeCrystal>() {

			@Override
			public Render<? super EntityEyeCrystal> createRenderFor(RenderManager manager) {
				return new EntityEyeCrystalRenderer(manager);
			}
			
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityFakeLight.class, new IRenderFactory<EntityFakeLight>() {

			@Override
			public Render<? super EntityFakeLight> createRenderFor(RenderManager manager) {
				return new Render<EntityFakeLight>(manager) {
					@Override
					protected ResourceLocation getEntityTexture(EntityFakeLight entity) {
						return null;
					}
				};
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(ChaosMasterBoss.class, new IRenderFactory<ChaosMasterBoss>() {

			@Override
			public Render<? super ChaosMasterBoss> createRenderFor(RenderManager manager) {
				return new CMRenderer(manager);
			}
			
		});
	}

}
