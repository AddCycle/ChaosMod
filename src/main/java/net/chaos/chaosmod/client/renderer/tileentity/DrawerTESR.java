package net.chaos.chaosmod.client.renderer.tileentity;

import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DrawerTESR extends TileEntitySpecialRenderer<TileEntityDrawer> {

	@Override
    public void render(TileEntityDrawer drawer, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (drawer == null) return;

        ItemStack stack = drawer.getStack();
        if (stack.isEmpty()) return;

        int count = stack.getCount();

        GlStateManager.pushMatrix();
        // Translate to the block position
        GlStateManager.translate(x + 0.5, y + 1.01, z + 0.5); // slightly above top surface

        // Face each side
        renderCountOnSide(count, 0); // north
        renderCountOnSide(count, 1); // south
        renderCountOnSide(count, 2); // west
        renderCountOnSide(count, 3); // east

        GlStateManager.popMatrix();
    }

    private void renderCountOnSide(int count, int side) {
        GlStateManager.pushMatrix();

        // Rotate to correct side
        switch (side) {
            case 0: GlStateManager.rotate(180, 0, 1, 0); GlStateManager.translate(0, 0, -0.51); break; // north
            case 1: GlStateManager.translate(0, 0, 0.51); break; // south
            case 2: GlStateManager.rotate(90, 0, 1, 0); GlStateManager.translate(0, 0, -0.51); break; // west
            case 3: GlStateManager.rotate(-90, 0, 1, 0); GlStateManager.translate(0, 0, -0.51); break; // east
        }

        double scale = 1;
        GlStateManager.scale(scale, scale, scale); // scale down text

        String text = String.valueOf(count);
        Minecraft.getMinecraft().fontRenderer.drawString(text, -Minecraft.getMinecraft().fontRenderer.getStringWidth(text)/2, 0, 0xFFFFFF);

        GlStateManager.popMatrix();
    }
}