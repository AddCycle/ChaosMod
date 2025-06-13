package net.chaos.chaosmod.client.gui.inventory;

import java.io.IOException;
import java.util.Collection;

import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.chaos.chaosmod.client.inventory.SlotAccessory;
import net.chaos.chaosmod.client.inventory.shield.SlotShield;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.sound.ClientSoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInventoryExtended extends GuiInventory {
	public static final ResourceLocation INV_TEXTURE = new ResourceLocation("chaosmod:textures/gui/slot_icon2.png");
	public static final ResourceLocation BUTTON_TEXTURE_ = new ResourceLocation("chaosmod:textures/gui/container/button_icons.png");
	private boolean showAccessorySlot = false;
	private boolean showShieldSlot = false;
    private GuiButton toggleButton;
	private Slot hoveredSlot;

	// ############# Music management ################
	// private ISound currentMusic = null;
	// private boolean isMusicPlaying = false;
	// Handled see : sound.ClientSoundHandler.java

	public GuiInventoryExtended(EntityPlayer player) {
        super(player);
        this.allowUserInput = true;
        this.inventorySlots = new ContainerAccessory(player.inventory, player.world.isRemote, player);
    }
	
	@Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(toggleButton = new GuiButton(99, this.guiLeft + 59, this.guiTop + 8, 15, 12, "") {
        	@Override
        	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

        		if (this.visible)
        		{
        			FontRenderer fontrenderer = mc.fontRenderer;
        			mc.getTextureManager().bindTexture(BUTTON_TEXTURE_);
        			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        			int i = this.getHoverState(this.hovered);
        			GlStateManager.enableBlend();
        			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        			this.drawTexturedModalRect(this.x, this.y, 182, 24, this.width, this.height);
        			if (this.hovered) {
        				this.drawTexturedModalRect(this.x, this.y, 166, 24, this.width, this.height);
        			}
        			this.mouseDragged(mc, mouseX, mouseY);
        			int j = 14737632;

        			if (packedFGColour != 0)
        			{
        				j = packedFGColour;
        			}
        			else
        				if (!this.enabled)
        				{
        					j = 10526880;
        				}
        				else if (this.hovered)
        				{
        					j = 16777120;
        				}

        			this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        		}
        		// super.drawButton(mc, mouseX, mouseY, partialTicks);

        	}
        });
        int o = 20; // offset
        int margin = 30;
        this.addButton(new GuiButton(100, this.guiLeft + 50, this.guiTop - o, 30, 20, "|>")); // TODO : texture the button
        this.addButton(new GuiButton(101, this.guiLeft + 50 + 30, this.guiTop - o, 30, 20, "P")); // TODO : texture the button
        this.addButton(new GuiButton(102, this.guiLeft + 50 + 60, this.guiTop - o, 30, 20, "S")); // TODO : texture the button
    }
	
	private void resetGuiLeft()
	{
		this.guiLeft = (this.width - this.xSize) / 2;
	}
	
	@Override 
	public void updateScreen()
	{
		updateActivePotionEffects();
		resetGuiLeft();
	}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Optional: Draw custom icon behind your slot
        if (showAccessorySlot) {
        	this.mc.getTextureManager().bindTexture(INV_TEXTURE);
        	drawModalRectWithCustomSizedTexture(
        	    this.guiLeft,  // x
        	    this.guiTop - 24,   // y
        	    0, 0,                    // u, v
        	    64, 24,                  // width, height
        	    64, 24                   // actual texture size
        	);
        }
        // this.drawTexturedModalRect(this.guiLeft - 12 - (32 / 2), this.guiTop - 16 - (32/2), 0, 0, 32, 32);
    }
    
    @Override
    protected void updateActivePotionEffects() {
        Collection<PotionEffect> effects = this.mc.player.getActivePotionEffects();
        this.hasActivePotionEffects = false;  // prevent vanilla drawing

        if (!effects.isEmpty()) {
            this.guiLeft = (this.width - this.xSize) / 2;  // shift GUI right to make room
        } else {
            this.guiLeft = (this.width - this.xSize) / 2;
        }
    }

    @Override
    protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_)
    {
    	super.renderHoveredToolTip(p_191948_1_, p_191948_2_);
    }
    
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	// Set visibility before drawing
        for (Slot slot : this.inventorySlots.inventorySlots) {
            if (slot instanceof SlotAccessory) {
                ((SlotAccessory) slot).visible = this.showAccessorySlot; // your toggle
            }
            if (slot instanceof SlotShield) {
                ((SlotShield) slot).visible = this.showShieldSlot; // your toggle
            }
        }

        // Prevent interaction with hidden slots
        Slot hoveredSlot = this.getSlotUnderMouse();
        if (hoveredSlot instanceof SlotAccessory && !((SlotAccessory) hoveredSlot).visible) {
            this.hoveredSlot = null;
        }
        if (hoveredSlot instanceof SlotShield && !((SlotShield) hoveredSlot).visible) {
            this.hoveredSlot = null;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        // Skip tooltip for hidden accessory slot
        if (!(hoveredSlot instanceof SlotAccessory && !((SlotAccessory) hoveredSlot).visible)) {
            renderHoveredToolTip(mouseX, mouseY);
        }
        if (!(hoveredSlot instanceof SlotShield && !((SlotShield) hoveredSlot).visible)) {
            renderHoveredToolTip(mouseX, mouseY);
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        Slot slot = this.getSlotUnderMouse();
        if (slot instanceof SlotAccessory && !((SlotAccessory) slot).visible || (slot instanceof SlotShield && !((SlotShield) slot).visible)) {
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    // TODO : add a switch statement or something else based on a registry
    protected void actionPerformed(GuiButton button) throws IOException {
    	if (button.id == 99) {
            showAccessorySlot = !showAccessorySlot;
            showShieldSlot = !showShieldSlot;
        } else if (button.id == 100) { // play
        	ClientSoundHandler.launchPlaylist();
        	// ClientSoundHandler.toggleMusic(ModSounds.FIRE_FORCE_OP);
        } else if (button.id == 101) { // pause/resume
        	System.out.println("Music paused/resumed");
        	if (!ClientSoundHandler.isMusicPaused() && ClientSoundHandler.isMusicPlaying()) ClientSoundHandler.pauseMusic();
        	else if (ClientSoundHandler.isMusicPaused() && !ClientSoundHandler.isMusicPlaying()) ClientSoundHandler.resumeMusic();
        } else if (button.id == 102) { // stop
        	System.out.println("Music stopped");
        	ClientSoundHandler.stopMusic();
        } else {
            super.actionPerformed(button);
        }
    }
    
    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    	// if (ClientSoundHandler.isMusicPaused())
    	// ClientSoundHandler.forcePause();
    }

}
