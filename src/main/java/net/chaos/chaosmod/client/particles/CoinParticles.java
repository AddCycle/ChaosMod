package net.chaos.chaosmod.client.particles;

import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class CoinParticles extends ParticleEndRod {
    private static final ResourceLocation COIN_TEXTURE = new ResourceLocation(Reference.MODID, "textures/particle/coin.png");
    private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    private int life;
    private final int lifeTime;
    private final TextureManager textureManager;
    private final float size;

	public CoinParticles(TextureManager textureManagerIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.textureManager = textureManagerIn;
        this.lifeTime = 4;
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.size = 1.0F - (float)xSpeedIn * 0.5F;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.life;

        if (this.life == this.lifeTime)
        {
            this.setExpired();
        }
    }

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        int i = (int)(((float)this.life + partialTicks) * 3.0F / (float)this.lifeTime);

        if (i <= 7)
        {
            this.textureManager.bindTexture(COIN_TEXTURE);
            float f = (float)(i % 4) / 4.0F;
            float f1 = f + 0.24975F;
            float f2 = (float)(i / 2) / 2.0F;
            float f3 = f2 + 0.4995F;
            float f4 = 1.0F * this.size;
            float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
            float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
            float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            buffer.begin(7, VERTEX_FORMAT);
            buffer.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4 * 0.5F), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4 * 0.5F), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4 * 0.5F), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4 * 0.5F), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableLighting();
        }
    }

}
