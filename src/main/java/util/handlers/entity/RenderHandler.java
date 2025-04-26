package util.handlers.entity;

import net.chaos.chaosmod.entity.EntityForgeGuardian;
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
	}

}
