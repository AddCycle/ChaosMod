package net.chaos.chaosmod.client.renderer.tileentity;
import net.chaos.chaosmod.blocks.BlockDrawer;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DrawerTESR extends TileEntitySpecialRenderer<TileEntityDrawer> {

	@Override
    public void render(TileEntityDrawer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
	    int count = te.getStack().getCount();
	    ItemStack stack = te.getStack();
		GlStateManager.pushMatrix();
	    GlStateManager.translate(x + 0.5, y + 0.5, z); // center on the block

	    // Base rotation for north (your reference)
	    GlStateManager.rotate(180, 0, 0, 1);

	    // Rotate based on facing
	    EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockDrawer.FACING);
	    switch(facing) {
	        case NORTH: break; // already rotated
	        case SOUTH:
	        	GlStateManager.rotate(180, 0, 1, 0);
	        	GlStateManager.translate(0, 0, -1);
	        	break;
	        case WEST: 
	        	GlStateManager.rotate(-90, 0, 1, 0);
	        	GlStateManager.translate(0.5, 0, -0.5);
	        	break;
	        case EAST: 
	        	GlStateManager.rotate(90, 0, 1, 0);
	        	GlStateManager.translate(-0.5, 0, -0.5);
	        	break;
	        default: break;
	    }

	    if(count >= 0) {
	        String text = String.valueOf(count);
	        String item = stack.getItem().getRegistryName().toString();

	        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	        GlStateManager.pushMatrix();
	        GlStateManager.disableDepth();
	        
	        GlStateManager.pushMatrix();
	        float scale = 0.5f;
	        GlStateManager.scale(scale, scale, 0.01f);
	        GlStateManager.translate(0, 0, -0.2);

	        // Keep depth test ON to occlude blocks behind
	        GlStateManager.enableDepth();

	        // Disable depth writing so drawer stays flat
	        GlStateManager.depthMask(false);

	        // Use GUI lighting instead of world lighting
	        RenderHelper.disableStandardItemLighting();
	        Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GUI);

	        // Restore depth write
	        GlStateManager.depthMask(true);
	        GlStateManager.popMatrix();

	        GlStateManager.pushMatrix();
	        GlStateManager.scale(0.02, 0.02, 0.02);
	        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 0xFFFFFF);
	        GlStateManager.popMatrix();

	        GlStateManager.pushMatrix();
	        GlStateManager.scale(0.01, 0.01, 0.01); // smaller scale
	        if (count > 0) fontRenderer.drawString(item, -fontRenderer.getStringWidth(item) / 2, 15 + fontRenderer.FONT_HEIGHT, 0xFFFFFF);
	        GlStateManager.popMatrix();

	        GlStateManager.enableDepth();
	        GlStateManager.popMatrix();
	    }

	    GlStateManager.popMatrix();
	}
}