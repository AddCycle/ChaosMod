package net.chaos.chaosmod.entity.animal.container;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.chaos.chaosmod.entity.inventory.SimpleEntityInventory;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBear extends Container {
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
	private final EntityPlayer player;
	private final EntityLiving entityBear;

	public ContainerBear(EntityPlayer player, EntityLiving entityBear) {
		this.player = player;
		this.entityBear = entityBear;
		this.initContainer();
	}

	private void initContainer() {
		InventoryPlayer playerInventory = player.inventory;
		Iterable<ItemStack> armorList = entityBear.getArmorInventoryList();
		List<ItemStack> list = Lists.newArrayList(armorList);
		IInventory inv = new SimpleEntityInventory(entityBear, list, 4);

        for (int k = 0; k < 4; ++k)
        {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(inv, 3 - k, 8, 8 + k * 18)
            {
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace
                 * fuel.
                 */
                // ArmorMaterial.IRON only for now, as I go, add more textures
                public boolean isItemValid(ItemStack stack)
                {
                	Item item = stack.getItem();
                	if (item.isValidArmor(stack, entityequipmentslot, entityBear) && (item instanceof ItemArmor)) {
                		ItemArmor armor = (ItemArmor) item;
                		ArmorMaterial material = armor.getArmorMaterial();
                		return material == ArmorMaterial.IRON
                			|| material == ModItems.ARMOR_MATERIAL_OXONIUM;
                	}
                	return false;
                }

                /**
                 * Return whether this slot's stack can be taken from this slot.
                 */
                public boolean canTakeStack(EntityPlayer playerIn)
                {
                    ItemStack itemstack = this.getStack();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
                }
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }
            });
        }

        // 3 main inventory rows
        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        // hotbar
        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}