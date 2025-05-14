package util.handlers.entity;

import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.boss.renderer.EntityMountainGiantBossRenderer;
import net.chaos.chaosmod.entity.boss.renderer.EntityRevengeBlazeRenderer;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.chaos.chaosmod.entity.projectile.render.EntityRockRenderer;
import net.chaos.chaosmod.entity.projectile.render.EntitySmallBlueFireballRenderer;
import net.chaos.chaosmod.entity.render.RenderChaosSage;
import net.chaos.chaosmod.entity.render.RenderForgeGuardian;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
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
	}

}
