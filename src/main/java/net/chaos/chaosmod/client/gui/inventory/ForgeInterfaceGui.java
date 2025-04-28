package net.chaos.chaosmod.client.gui.inventory;

import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
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
    }

    private int getFabricationProcess(int pixels)
    {
        int i = this.tileEntity.getField(0);
        int j = this.tileEntity.getField(1);
        int k = this.tileEntity.getField(2);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
    
}
