package net.chaos.chaosmod.client.gui.inventory;

import net.chaos.chaosmod.inventory.BackpackContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class BackpackGui extends GuiContainer {
	private static final ResourceLocation BACKPACK_GUI_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
    private final InventoryPlayer playerInventory;
    private final int inventoryRows;

    public BackpackGui(BackpackContainer container) {
        super(container);
        this.playerInventory = container.inventorySlots.get(0).inventory instanceof InventoryPlayer
            ? (InventoryPlayer) container.inventorySlots.get(0).inventory : null;
        this.xSize = 222;
        this.inventoryRows = 27 / 9; // 3x9
        this.ySize = 114 + this.inventoryRows * 18;
    }
    
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Backpack", 8, 6, 0x404040);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BACKPACK_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

	public InventoryPlayer getPlayerInventory() {
		return playerInventory;
	}
}
