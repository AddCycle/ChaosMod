package net.chaos.chaosmod.world.events;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.items.special.TinkerersHammer;
import net.chaos.chaosmod.network.PacketOpenAccessoryGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class PlayerLifeEvents {
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		ItemStack held = player.getHeldItemMainhand();
		
		if (!boots.isEmpty() && boots.getItem() instanceof OxoniumBoots) {
			player.stepHeight = 1.0f;
		} else {
			player.stepHeight = 0.6f;
		}
		
		if (!held.isEmpty() && held.getItem() instanceof TinkerersHammer) {
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1, 1, false, false));
		}
		
		IAccessory cap = player.getCapability(ModCapabilities.ACCESSORY, null);
	    if (cap == null) return;

	    ItemStack accessory = cap.getAccessoryItem();
	    if (!accessory.isEmpty() && accessory.getItem() instanceof OxoniumNecklace) {
	        if (!player.isPotionActive(MobEffects.SPEED)) {
	            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 0, false, false));
	        }
	    }
		
	    /*ItemStack held = player.getHeldItemMainhand();

	    if (held.getItem() == Item.getItemFromBlock(ModBlocks.LANTERN)) {
	        // Spawn or update invisible light entity at player’s position
	        LightEntityManager.updateLightEntity(player);
	    } else {
	        // Remove light entity
	        LightEntityManager.removeLightEntity();
	    }*/
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
	    Minecraft mc = Minecraft.getMinecraft();
	    EntityPlayer player = mc.player;
	    // if (!player.isCreative()) return;

	    if (mc.inGameHasFocus && mc.currentScreen == null && Keyboard.getEventKeyState()) {
	        if (Keyboard.getEventKey() == mc.gameSettings.keyBindInventory.getKeyCode()) {
	            Main.network.sendToServer(new PacketOpenAccessoryGui());
	        }
	    }
	}
	
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
	    if (event.getObject() instanceof EntityPlayer) {
	    	event.addCapability(new ResourceLocation("chaosmod", "accessory"), new ICapabilitySerializable<NBTTagCompound>() {
	    	    final AccessoryImpl instance = new AccessoryImpl();

	    	    @Override
	    	    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	    	        return capability == ModCapabilities.ACCESSORY;
	    	    }

	    	    @Override
	    	    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	    	        return capability == ModCapabilities.ACCESSORY ? ModCapabilities.ACCESSORY.cast(instance) : null;
	    	    }

	    	    @Override
	    	    public NBTTagCompound serializeNBT() {
	    	    	System.out.println("Serializing accessory capability");
	    	        return instance.serializeNBT(); // very important!
	    	    }

	    	    @Override
	    	    public void deserializeNBT(NBTTagCompound nbt) {
	    	    	System.out.println("Deserializing accessory capability");
	    	        instance.deserializeNBT(nbt);
	    	    }
	    	});
	    }
	}
	
	@SubscribeEvent
	// TODO : to avoid it repeatedly spawning -> player.getEntityData().setBoolean("chaos_has_spawned", true);
    public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        if (event.toDim == -1) {
            World world = player.world;

            if (!world.isRemote) {
                BlockPos pos = player.getPosition().add(2, 0, 2);
                EntityChaosSage entity = new EntityChaosSage(world);
                entity.setPosition(pos.getX(), pos.getY(), pos.getZ());

                world.spawnEntity(entity);
            }
        }
    }

}
