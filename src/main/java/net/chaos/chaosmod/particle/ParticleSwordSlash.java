package net.chaos.chaosmod.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleSwordSlash extends Particle {

    public ParticleSwordSlash(World worldIn, double x, double y, double z, float red, float green, float blue) {
        super(worldIn, x, y, z);
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.particleAlpha = 0.9f;
        this.particleMaxAge = 6;
        this.setSize(0.5f, 0.5f);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks,
                               float rotationX, float rotationZ, float rotationYZ,
                               float rotationXY, float rotationXZ) {
        float scale = 0.4f * (1.0f - ((float) this.particleAge / this.particleMaxAge));
        GlStateManager.color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/particle/particles.png"));
        Tessellator tessellator = Tessellator.getInstance();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

        double px = this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interp(entityIn.posX);
        double py = this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interp(entityIn.posY);
        double pz = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interp(entityIn.posZ);

        double uMin = 0.0;
        double uMax = 1.0;
        double vMin = 0.0;
        double vMax = 1.0;

        buffer.pos(px - scale, py - scale, pz).tex(uMin, vMax).color(particleRed, particleGreen, particleBlue, particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos(px - scale, py + scale, pz).tex(uMin, vMin).color(particleRed, particleGreen, particleBlue, particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos(px + scale, py + scale, pz).tex(uMax, vMin).color(particleRed, particleGreen, particleBlue, particleAlpha).lightmap(240, 240).endVertex();
        buffer.pos(px + scale, py - scale, pz).tex(uMax, vMax).color(particleRed, particleGreen, particleBlue, particleAlpha).lightmap(240, 240).endVertex();

        tessellator.draw();
    }

    private double interp(double value) {
        return Minecraft.getMinecraft().getRenderManager().viewerPosX;
    }
}
