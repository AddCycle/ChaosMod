package net.chaos.chaosmod.client.renderer.tileentity;

import net.chaos.chaosmod.tileentity.TileEntityATM;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class AtmTESR extends TileEntitySpecialRenderer<TileEntityATM> {
	
	@Override
	public void render(TileEntityATM te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		GlStateManager.pushMatrix();

        // Translate to the location where the TileEntity is placed in the world
        GlStateManager.translate(x + 0.5, y, z + 0.5);

        // Rotate if needed (example: based on block facing)
        // GlStateManager.rotate(angle, 0F, 1F, 0F);

        // Scale if needed
        // GlStateManager.scale(1.0F, 1.0F, 1.0F);

        // Bind your texture
        // this.bindTexture(new ResourceLocation("yourmodid:textures/blocks/your_model.png"));

        // Render your model (if you exported a Blockbench Java model, you’ll have a class for it)
        // YourBlockbenchModel model = new YourBlockbenchModel();
        // model.render(te, 0F, 0F, 0F, 0F, 0F, 0.0625F);

        GlStateManager.popMatrix();
	}
	
	@Override
	protected void drawNameplate(TileEntityATM te, String str, double x, double y, double z, int maxDistance) {
		if (str == null) return;
		super.drawNameplate(te, str, x, y, z, maxDistance);
	}

}
