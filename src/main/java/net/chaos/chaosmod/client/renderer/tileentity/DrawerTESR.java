package net.chaos.chaosmod.client.renderer.tileentity;
import java.util.List;

import net.chaos.chaosmod.blocks.BlockDrawer;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
// FIXME : flowers & saplings (cross) are not rendering correctly now invis
public class DrawerTESR extends TileEntitySpecialRenderer<TileEntityDrawer> {

	@Override
	public void render(TileEntityDrawer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		int count = te.getStack().getCount();
		// System.out.println("count : " + count);
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

			// Get tooltip
			List<String> tooltip = stack.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL);

			/*String nbtText = "";
		    if (stack.hasTagCompound()) {
		        nbtText = stack.getTagCompound().toString();
		        // Optional: truncate if too long
		        if (nbtText.length() > 50) {
		            nbtText = nbtText.substring(0, 47) + "...";
		        }
		    }*/

			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			GlStateManager.pushMatrix();
			GlStateManager.disableDepth();

			Block block = Block.getBlockFromItem(stack.getItem());
			if (block == Blocks.AIR) {
				// It's a normal item → keep normal Z, depth test + depth write
				GlStateManager.pushMatrix();
				float scale = 0.5f;
				GlStateManager.scale(scale, scale, -0.02);
				GlStateManager.translate(0,0,1);
				GlStateManager.rotate(180, 1, 0, 0);

				// Keep depth test ON to occlude blocks behind
				GlStateManager.enableDepth();

				// Disable depth writing so drawer stays flat
				GlStateManager.depthMask(false);

				// Use GUI lighting instead of world lighting
				// turns off OpenGL lighting
				GlStateManager.disableLighting();
				GlStateManager.disableBlend();
				GlStateManager.enableRescaleNormal();

				// Force maximum lightmap (fullbright)
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GUI);

				// Restore depth write
				GlStateManager.depthMask(true);
				GlStateManager.popMatrix();
			} else {
				// It's a block → you can flatten it and disable depth writing
				GlStateManager.pushMatrix();
				float scale = 0.5f;
				GlStateManager.scale(scale, scale, 0.01f);
				GlStateManager.translate(0, 0, -0.2);

				// Keep depth test ON to occlude blocks behind
				GlStateManager.enableDepth();

				// Disable depth writing so drawer stays flat
				GlStateManager.depthMask(false);

				// Use GUI lighting instead of world lighting
				GlStateManager.disableLighting();
				GlStateManager.disableBlend();
				GlStateManager.enableRescaleNormal();

				// Force maximum lightmap (fullbright)
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GUI);

				// Restore depth write
				GlStateManager.depthMask(true);
				GlStateManager.popMatrix();
			}

			GlStateManager.pushMatrix();

			// Move text slightly in front of the block face
			float zOffset = 0.01f; // tiny offset to avoid z-fighting
			GlStateManager.translate(0, 0, -zOffset);

			// Scale text
			GlStateManager.scale(0.02, 0.02, 0.02);

			fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0, 0xFFFFFF);
			GlStateManager.popMatrix();

			// REGISTRY NAME
			/*GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, -zOffset);
			GlStateManager.scale(0.01, 0.01, 0.02);
			if (count > 0) fontRenderer.drawString(item, -fontRenderer.getStringWidth(item) / 2, 15 + fontRenderer.FONT_HEIGHT, 0xFFFFFF);
			GlStateManager.popMatrix();*/

			// Render tooltip lines
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, -zOffset);
			GlStateManager.scale(0.008, 0.008, 0.02); // small scale
			int yOffset = 25 + fontRenderer.FONT_HEIGHT;
			for (String line : tooltip) {
				if (!line.contains("Air")) {
					fontRenderer.drawString(line, -fontRenderer.getStringWidth(line) / 2, yOffset, 0xFFFFFF);
					yOffset += fontRenderer.FONT_HEIGHT + 2; // spacing
				}
			}
			GlStateManager.popMatrix();

			// Render NBT
			/*if (!nbtText.isEmpty()) {
		        GlStateManager.pushMatrix();
		        GlStateManager.translate(0, 0, -zOffset);
		        GlStateManager.scale(0.008, 0.008, 0.02); // smaller so it fits
		        fontRenderer.drawString(nbtText, -fontRenderer.getStringWidth(nbtText) / 2, 35 + fontRenderer.FONT_HEIGHT, 0xFFFFFF);
		        GlStateManager.popMatrix();
		    }*/

			GlStateManager.enableDepth();
			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
	}
}