package net.chaos.chaosmod.items.armor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.chaos.chaosmod.network.packets.JumpPacket;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class ItemWings extends ArmorBase {
	private static final Map<UUID, Integer> jumps = new HashMap<>();
	private static int MAX_JUMPS = 100;

	public ItemWings(String name, ArmorMaterial materialIn) {
		super(name, materialIn, 1, EntityEquipmentSlot.CHEST);
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		return new ModelWings();
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return super.getArmorTexture(stack, entity, slot, type);
	}
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.phase != Phase.END) return;
		
		EntityPlayer player = event.player;

		ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		if (!(chest.getItem() instanceof ItemWings)) return;

		if (event.side.isClient()) {
			if (player.onGround) {
	            jumps.put(player.getUniqueID(), 0);
	        }
			
			if (checkKeyPress()) {
				int used = jumps.getOrDefault(player.getUniqueID(), 0);

				boolean flag = player.motionY < 0;
				boolean limit = used < MAX_JUMPS - 1; // FIXME : balance maybe test again later-on
	            if (!player.onGround && flag) {

	                jumps.put(player.getUniqueID(), used + 1);
	                
	                PacketManager.network.sendToServer(new JumpPacket());
	            }
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean checkKeyPress() {
		return Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();
	}
}