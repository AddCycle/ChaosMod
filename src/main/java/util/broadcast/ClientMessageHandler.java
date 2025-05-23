package util.broadcast;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientMessageHandler {
	private static String currentMessage = "";
    private static long displayUntil = 0;

    public static void displayMessage(String message) {
        currentMessage = message;
        displayUntil = System.currentTimeMillis() + 3000; // 3 seconds
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (!currentMessage.isEmpty() && System.currentTimeMillis() < displayUntil) {
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(mc);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            // GlStateManager.pushMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();

            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); // Safe default
            mc.fontRenderer.drawStringWithShadow(
                currentMessage,
                width / 2 - mc.fontRenderer.getStringWidth(currentMessage) / 2,
                height / 4,
                0xFFFFFF
            );
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); // Restore color
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();

            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
            // GlStateManager.popMatrix();
        }
    }

}
