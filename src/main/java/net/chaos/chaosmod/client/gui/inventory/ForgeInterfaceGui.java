package net.chaos.chaosmod.client.gui.inventory;

import java.io.IOException;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class ForgeInterfaceGui extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/forge_interface.png");
	private static final ResourceLocation ALL_IN_ONE_BOW = new ResourceLocation(Reference.MODID, "textures/items/all_in_one_bow.png");
	private static final ResourceLocation OXONIUM_BOW = new ResourceLocation(Reference.MODID, "textures/items/oxonium_bow_standby.png");
	private static final ResourceLocation ALL_IN_ONE_SWORD = new ResourceLocation(Reference.MODID, "textures/items/all_in_one_sword.png");
	private static final ResourceLocation UNKNOWN = new ResourceLocation("minecraft", "textures/gui/world_selection.png");
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
		Minecraft mc = Minecraft.getMinecraft();
        this.fontRenderer.drawString(this.tileEntity.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        // int offset = 22;
        int buttonSize = 20;
        int renderItemX = this.guiLeft + 24;
        int renderItemY = this.guiTop + 13;
        // Minecraft.getMinecraft().getTextureManager().bindTexture(OXONIUM_BOW);
        // this.drawTexturedModalRect(this.guiLeft+40, this.guiTop+20, 0, 0, 16, 16);
        ItemStack bowStack = new ItemStack(ModItems.ALL_IN_ONE_BOW);
        ItemStack swordStack = new ItemStack(ModItems.ALL_IN_ONE_SWORD);
        // ItemStack pickaxeStack = new ItemStack(ModItems.ALL_IN_ONE_PICKAXE);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(swordStack, renderItemX, renderItemY - buttonSize - 2);
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(bowStack, renderItemX, renderItemY - 2 * (buttonSize + 2));
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(bowStack, renderItemX, renderItemY);
        Minecraft.getMinecraft().getTextureManager().bindTexture(UNKNOWN);
        this.drawTexturedModalRect(this.guiLeft+37, this.guiTop+18, 102, 37, 6, 22);
        RenderHelper.disableStandardItemLighting();
        // Gui.drawModalRectWithCustomSizedTexture(0, 272, 0, 0, 20, 20, 20, 20);
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
    
    @Override
    public void initGui() {
    	super.initGui();
    	int offset = 22;
        int buttonSize = 20;
        GuiButton button1 = new GuiButton(0, 272, 50, buttonSize, buttonSize, "");
        GuiButton button2 = new GuiButton(1, 272, 50 + offset, buttonSize, buttonSize, "");
        this.addButton(button1);
        this.addButton(button2);
        this.addButton(new GuiButton(2, 272, 50 + offset * 2, buttonSize, buttonSize, ""));
    }
    
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
    	switch (button.id) {
        case 0:
        	// send the crafting for bow
            break;
        case 1:
        	// trigger the crafting for sword
            break;
        case 2:
            // Do nothing for now TODO:all_in_one_pickaxe
            break;
        default: break;
    }
    }
    
    
    
}
