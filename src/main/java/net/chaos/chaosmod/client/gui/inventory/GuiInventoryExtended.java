package net.chaos.chaosmod.client.gui.inventory;

import java.util.Collection;

import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInventoryExtended extends GuiInventory {

	public GuiInventoryExtended(EntityPlayer player) {
        super(player);
        this.allowUserInput = true;
        this.inventorySlots = new ContainerAccessory(player.inventory, player.world.isRemote, player);
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
        this.mc.getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/gui/slot_icon2.png"));
        drawModalRectWithCustomSizedTexture(
        	    this.guiLeft,  // x
        	    this.guiTop - 24,   // y
        	    0, 0,                    // u, v
        	    64, 24,                  // width, height
        	    64, 24                   // actual texture size
        	);
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Replace super.drawScreen() to avoid calling drawActivePotionEffects()

        // this.guiLeft;
    	super.drawScreen(mouseX, mouseY, partialTicks);

        /*this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        // No call to drawActivePotionEffects() here!

        super.renderHoveredToolTip(mouseX, mouseY); // Still render tooltips
        RenderHelper.enableGUIStandardItemLighting();*/

        // Now call your custom method
        // drawCustomActivePotionEffects();
    }    

    private void drawCustomActivePotionEffects() {
        // You can copy and customize the logic from the original drawActivePotionEffects()
        // Here is a simplified example that draws potion icons and duration:

        // Get player's active potion effects
        Collection<PotionEffect> effects = this.mc.player.getActivePotionEffects();

        if (effects.isEmpty()) {
            return;
        }

        int x = this.guiLeft - 120;  // Draw left of the inventory GUI
        int y = this.guiTop + 32;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for (PotionEffect effect : effects) {
            Potion potion = effect.getPotion();

            // Draw the potion icon background
            this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
            this.drawTexturedModalRect(x, y, 0, 166, 140, 32);

            // Draw potion icon if available
            if (potion.hasStatusIcon()) {
                int iconIndex = potion.getStatusIconIndex();
                this.drawTexturedModalRect(x + 6, y + 7, 18 + (iconIndex % 8) * 18, 198 + (iconIndex / 8) * 18, 18, 18);
            }

            // Draw potion name
            String potionName = I18n.format(potion.getName());
            if (effect.getAmplifier() > 0) {
                potionName = potionName + " " + I18n.format("enchantment.level." + (effect.getAmplifier() + 1));
            }
            this.fontRenderer.drawStringWithShadow(potionName, x + 10 + 18, y + 6, 0xFFFFFF);

            // Draw potion duration
            String duration = Potion.getPotionDurationString(effect, 1.0F);
            this.fontRenderer.drawStringWithShadow(duration, x + 10 + 18, y + 6 + 10, 0x7F7F7F);

            y += 32;  // Move down for next effect
        }
    }

}
