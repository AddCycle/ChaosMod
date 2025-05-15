package net.chaos.chaosmod.client.gui.inventory;

import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class ForgeInterfaceGui extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/forge_interface.png");
    private final InventoryPlayer playerInventory;
    private final IInventory tileEntity;

	public ForgeInterfaceGui(InventoryPlayer playerInv, IInventory tileEntity) {
		super(new ForgeInterfaceContainer(playerInv, tileEntity));
		this.playerInventory = playerInv;
		this.tileEntity = tileEntity;
		this.xSize = 176;
        this.ySize = 166;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(this.tileEntity.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        // x, y = GUI top-left on screen
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        // arrow width and height
        int arrowWidth = 24;
        int arrowHeight = 16;
        
        // hammer parts
        int hammerWidth = 18;
        int hammerHeight = 17;

        // arrow position on GUI screen
        int arrowX = x + 76;
        int arrowY = y + 34;
        int hammerX = x + 14;
        int hammerY = y + 16;

        // arrow position in your texture atlas
        int textureU = 176; // horizontal offset in texture
        int textureV = 14;  // vertical offset in texture
        int hammerV = 31;

        int progress = ((TileEntityForge) tileEntity).getProgressScaled(arrowWidth);
        int progress_ = ((TileEntityForge) tileEntity).getProgressScaled(hammerWidth);

        this.drawTexturedModalRect(arrowX, arrowY, textureU, textureV, progress, arrowHeight);
        this.drawTexturedModalRect(hammerX, hammerY, textureU, hammerV, progress, hammerHeight);
        this.drawTexturedModalRect(hammerX + 18, hammerY + 36, textureU, hammerV, progress, hammerHeight);
        this.drawTexturedModalRect(hammerX + 18 * 2, hammerY, textureU, hammerV, progress, hammerHeight);
    }

    private int getFabricationProcess(int pixels)
    {
        int i = this.tileEntity.getField(0);
        int j = this.tileEntity.getField(1);
        int k = this.tileEntity.getField(2);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
}
