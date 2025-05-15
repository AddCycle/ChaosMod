package net.chaos.chaosmod.client.inventory;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Optional: Draw custom icon behind your slot
        this.mc.getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/gui/slot_icon.png"));
        this.drawTexturedModalRect(this.guiLeft - 12, this.guiTop + 8, 0, 0, 16, 16);
    }
}
