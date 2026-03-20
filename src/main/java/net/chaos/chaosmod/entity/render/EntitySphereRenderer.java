package net.chaos.chaosmod.entity.render;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.EntitySphere;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntitySphereRenderer extends Render<EntitySphere> {
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

	public EntitySphereRenderer(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntitySphere entity, double x, double y, double z, float entityYaw, float partialTicks) {

		int stacks = 10; // vertical
		int slices = 10; // horizontal
		float radius = 1f;
		
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, z);
		
		GlStateManager.disableCull();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

		renderSphere(buffer, radius, stacks, slices);

		tessellator.draw();
		
		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	public static void renderSphere(BufferBuilder buffer, float radius, int stacks, int slices) {

	    for (int i = 0; i < stacks; i++) {
	        float phi1 = (float) Math.PI * i / stacks;
	        float phi2 = (float) Math.PI * (i + 1) / stacks;

	        for (int j = 0; j < slices; j++) {
	            float theta1 = (float) (2 * Math.PI * j / slices);
	            float theta2 = (float) (2 * Math.PI * (j + 1) / slices);

	            Vec3d v1 = getPoint(radius, phi1, theta1);
	            Vec3d v2 = getPoint(radius, phi2, theta1);
	            Vec3d v3 = getPoint(radius, phi2, theta2);
	            Vec3d v4 = getPoint(radius, phi1, theta2);

//	            int r = 255, g = 255, b = 255, a = 255;
	            int r = 255, g = 255, b = 0, a = 255;
	            buffer.pos(v1.x, v1.y, v1.z).color(r, g, b, a).endVertex();
	            buffer.pos(v2.x, v2.y, v2.z).color(r, g, b, a).endVertex();
	            buffer.pos(v3.x, v3.y, v3.z).color(r, g, b, a).endVertex();

	            buffer.pos(v1.x, v1.y, v1.z).color(r, g, b, a).endVertex();
	            buffer.pos(v3.x, v3.y, v3.z).color(r, g, b, a).endVertex();
	            buffer.pos(v4.x, v4.y, v4.z).color(r, g, b, a).endVertex();
	        }
	    }
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySphere entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Vec3d getPoint(float r, float phi, float theta) {
	    float x = (float)(r * Math.sin(phi) * Math.cos(theta));
	    float y = (float)(r * Math.cos(phi));
	    float z = (float)(r * Math.sin(phi) * Math.sin(theta));
	    return new Vec3d(x, y, z);
	}
}