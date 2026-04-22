package net.chaos.chaosmod.entity.animal.gui;

import net.chaos.chaosmod.entity.animal.container.ContainerBear;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBear extends GuiContainer {
    /** The old x position of the mouse pointer */
    private float oldMouseX;
    /** The old y position of the mouse pointer */
    private float oldMouseY;

	private final IInventory playerInventory;
	private final EntityLiving entityBear;

	public GuiBear(EntityPlayer player, EntityLiving entityBear) {
		super(new ContainerBear(player, entityBear));
		this.playerInventory = player.inventory;
		this.entityBear = entityBear;
		this.allowUserInput = false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);

        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        int scale = 20; // 30
        GuiInventory.drawEntityOnScreen(i + 51, j + 75, scale, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.entityBear);
	}

}
