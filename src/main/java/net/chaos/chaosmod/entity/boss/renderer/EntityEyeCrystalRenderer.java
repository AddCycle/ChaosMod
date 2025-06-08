package net.chaos.chaosmod.entity.boss.renderer;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.boss.entities.EntityEyeCrystal;
import net.chaos.chaosmod.entity.boss.model.EntityEyeCrystalModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import util.Reference;

public class EntityEyeCrystalRenderer extends Render<EntityEyeCrystal> {

    public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation(Reference.MODID, "textures/entity/eyecrystal/eyecrystal_beam.png");
	private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation(Reference.MODID, "textures/entity/eyecrystal/eyecrystal.png");
    private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
    private final ModelBase modelEnderCrystal = new EntityEyeCrystalModel(0.0F, true);
    private final ModelBase modelEnderCrystalNoBase = new EntityEyeCrystalModel(0.0F, false);

    public EntityEyeCrystalRenderer(RenderManager renderManagerIn) {
		super(renderManagerIn);
        this.shadowSize = 0.5F;
	}

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityEyeCrystal entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float f = (float)entity.innerRotation + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        this.bindTexture(ENDER_CRYSTAL_TEXTURES);
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        if (entity.shouldShowBottom())
        {
            this.modelEnderCrystal.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        }
        else
        {
            this.modelEnderCrystalNoBase.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        }

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        BlockPos blockpos = entity.getBeamTarget();

        if (blockpos != null)
        {
            this.bindTexture(ENDERCRYSTAL_BEAM_TEXTURES);
            float f2 = (float)blockpos.getX() + 0.5F;
            float f3 = (float)blockpos.getY() + 0.5F;
            float f4 = (float)blockpos.getZ() + 0.5F;
            double d0 = (double)f2 - entity.posX;
            double d1 = (double)f3 - entity.posY;
            double d2 = (double)f4 - entity.posZ;
            RenderDragon.renderCrystalBeams(x + d0, y - 0.3D + (double)(f1 * 0.4F) + d1, z + d2, partialTicks, (double)f2, (double)f3, (double)f4, entity.innerRotation, entity.posX, entity.posY, entity.posZ);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        // System.out.println("trigger");
        if (entity.getLaserTarget() != null) {
        	Entity target = entity.getLaserTarget();
            if (target != null) {
                double tx = target.lastTickPosX + (target.posX - target.lastTickPosX) * partialTicks;
                double ty = target.lastTickPosY + target.getEyeHeight() + (target.posY - target.lastTickPosY) * partialTicks;
                double tz = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * partialTicks;

                double sx = x;
                double sy = y + entity.getEyeHeight();
                double sz = z;

                // Render beam from (sx,sy,sz) to (tx,ty,tz)
                renderBeam(sx, sy, sz, tx - entity.posX, ty - entity.posY, tz - entity.posZ, partialTicks);
            }
        	// System.out.println("No trigger");
            // renderLaserBeam(entity, entity.getLaserTarget(), x, y, z, partialTicks);
        }
    }
    
    private void renderBeam(double x, double y, double z, double dx, double dy, double dz, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.glLineWidth(4.0F);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, z).color(0.0F, 0.5F, 1.0F, 1.0F).endVertex();
        buffer.pos(x + dx, y + dy, z + dz).color(0.0F, 0.5F, 1.0F, 1.0F).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    
    private void renderLaserBeam(EntityEyeCrystal source, Entity entity, double x, double y, double z, float partialTicks) {
        double startX = x;
        double startY = y + source.getEyeHeight();
        double startZ = z;

        double dx = entity.posX - source.posX;
        double dy = (entity.posY + entity.getEyeHeight()) - (source.posY + source.getEyeHeight());
        double dz = entity.posZ - source.posZ;

        int segments = 20;
        for (int i = 0; i <= segments; i++) {
            double t = i / (double)segments;
            double px = startX + dx * t;
            double py = startY + dy * t;
            double pz = startZ + dz * t;
            source.world.spawnParticle(EnumParticleTypes.REDSTONE, px, py, pz, 1, 0, 0); // red laser
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityEyeCrystal entity)
    {
        return ENDER_CRYSTAL_TEXTURES;
    }

    public boolean shouldRender(EntityEyeCrystal livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
        return super.shouldRender(livingEntity, camera, camX, camY, camZ) || livingEntity.getBeamTarget() != null;
    }

}
