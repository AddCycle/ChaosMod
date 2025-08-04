package net.chaos.chaosmod.client.inventory.render;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.client.inventory.ClientAccessoryData;
import net.chaos.chaosmod.items.shield.AllemaniteShield;
import net.chaos.chaosmod.items.shield.OxoniumShield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class LayerShield implements LayerRenderer<AbstractClientPlayer> {
	private static final ResourceLocation SHIELD_TEXTURE = new ResourceLocation(Reference.MODID, "textures/items/oxonium_shield.png");
	private static final ResourceLocation ALLEMANITE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/items/allemanite_shield.png");
	//	private static final ResourceLocation ENDERITE_TEXTURE = new ResourceLocation("chaosmod", "textures/entity/player/enderite_necklace.png");
    private final RenderPlayer renderPlayer;
    
    public LayerShield(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	if (player == null || player.isInvisible()) return;
    	
    	ItemStack shieldStack = ClientAccessoryData.getPlayerShield(player);
        if (shieldStack == null || shieldStack.isEmpty()) return;

        if (shieldStack.getItem() instanceof OxoniumShield) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_TEXTURE);
        } else if (shieldStack.getItem() instanceof AllemaniteShield) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(ALLEMANITE_TEXTURE);
        }

    	GlStateManager.pushMatrix();

    	// Position roughly at chest — adjust Y and Z as needed
    	GlStateManager.translate(0.0F, 0.3F, 0.13F); 

    	// smaller than necklace
    	float scale_ = 0.5f;
    	GlStateManager.scale(scale_, scale_, scale_);

    	// Enable transparency and lighting fixes
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableCull();

    	Tessellator tessellator = Tessellator.getInstance();
    	BufferBuilder buffer = tessellator.getBuffer();

    	buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    	buffer.pos(-0.5, 0.5, 0).tex(0, 1).endVertex();
    	buffer.pos(0.5, 0.5, 0).tex(1, 1).endVertex();
    	buffer.pos(0.5, -0.5, 0).tex(1, 0).endVertex();
    	buffer.pos(-0.5, -0.5, 0).tex(0, 0).endVertex();
    	tessellator.draw();

    	GlStateManager.enableLighting();
    	GlStateManager.enableCull();
    	GlStateManager.disableBlend();

    	GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

	public RenderPlayer getRenderPlayer() {
		return renderPlayer;
	}
}