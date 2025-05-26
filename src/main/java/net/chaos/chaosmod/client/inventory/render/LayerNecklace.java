package net.chaos.chaosmod.client.inventory.render;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.client.inventory.ClientAccessoryData;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.items.necklace.AllemaniteNecklace;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
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

@SideOnly(Side.CLIENT)
public class LayerNecklace implements LayerRenderer<AbstractClientPlayer> {
	// private static final ResourceLocation NECKLACE_TEXTURE = new ResourceLocation("chaosmod", "textures/entity/player/oxonium_necklace.png");
	private static final ResourceLocation NECKLACE_TEXTURE = new ResourceLocation("chaosmod", "textures/entity/player/oxonium_necklace.png");
	private static final ResourceLocation ALLEMANITE_TEXTURE = new ResourceLocation("chaosmod", "textures/entity/player/allemanite_necklace.png");
    private final RenderPlayer renderPlayer;
    
    public LayerNecklace(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	if (player == null || player.isInvisible()) return;
    	
    	ItemStack necklaceStack = ClientAccessoryData.getPlayerNecklace(player);
        if (necklaceStack == null || necklaceStack.isEmpty()) return;
    	// ItemStack necklaceStack = player.getCapability(ModCapabilities.ACCESSORY, null).getAccessoryItem();
        /*if (necklaceStack.isEmpty() || !(necklaceStack.getItem() instanceof OxoniumNecklace)) {
            // Player is not wearing the necklace, don't render
            return;
        }*/

        if (necklaceStack.getItem() instanceof OxoniumNecklace) Minecraft.getMinecraft().getTextureManager().bindTexture(NECKLACE_TEXTURE);
        if (necklaceStack.getItem() instanceof AllemaniteNecklace) Minecraft.getMinecraft().getTextureManager().bindTexture(ALLEMANITE_TEXTURE);

    	GlStateManager.pushMatrix();

    	// Position roughly at chest — adjust Y and Z as needed
    	GlStateManager.translate(0.0F, 0.0F, -0.15F); 

    	// Rotate with player head so necklace stays on chest, you can add:
    	// GlStateManager.rotate(netHeadYaw, 0F, 1F, 0F);
    	// GlStateManager.rotate(headPitch, 1F, 0F, 0F);

    	// Scale to a reasonable size (try 0.5F or 1F)
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
}