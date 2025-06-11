package net.chaos.chaosmod.world.events;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.chaos.chaosmod.items.necklace.AllemaniteNecklace;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.items.special.TinkerersHammer;
import net.chaos.chaosmod.network.PacketOpenAccessoryGui;
import net.chaos.chaosmod.sound.ClientSoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.handlers.LightEntityManager;

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

		// FIXME : tried to make a light that is following the player
		/*ItemStack heldi = player.getHeldItemMainhand();
		World world = player.getEntityWorld();

		if (world.isRemote) {
			if (heldi.getItem() == Item.getItemFromBlock(ModBlocks.LANTERN)) {
				// Spawn or update invisible light entity at player’s position
				LightEntityManager.updateLightEntity(player);
			} else {
				// Remove light entity
				LightEntityManager.removeLightEntity();
			}
		}*/
		
		IAccessory cap = player.getCapability(ModCapabilities.ACCESSORY, null);
	    if (cap == null) return;

	    ItemStack accessory = cap.getAccessoryItem();
	    if (!accessory.isEmpty()) {
	    	if (accessory.getItem() instanceof OxoniumNecklace) {
	    		if (!player.isPotionActive(MobEffects.REGENERATION)) {
	    			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 * 3, 0, false, false));
	    		}
	        } else if (accessory.getItem() instanceof AllemaniteNecklace) { // TODO : add something for wither
	    		if (!player.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
	    			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20 * 3, 0, false, false));
	    		}
	        }
	    }
	    
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
	    Minecraft mc = Minecraft.getMinecraft();

	    if (mc.inGameHasFocus && mc.currentScreen == null && Keyboard.getEventKeyState()) {
	        if (Keyboard.getEventKey() == mc.gameSettings.keyBindInventory.getKeyCode()) {
	            Main.network.sendToServer(new PacketOpenAccessoryGui());
	        } else if (ModKeybinds.playMusicKey.isPressed()) {
	        	ClientSoundHandler.launchPlaylist();
	        } else if (ModKeybinds.pauseMusicKey.isPressed()) {
	        	if (ClientSoundHandler.isMusicPlaying() && !ClientSoundHandler.isMusicPaused()) ClientSoundHandler.pauseMusic();
	        	else ClientSoundHandler.resumeMusic();
	        } else if (ModKeybinds.stopMusicKey.isPressed()) {
	        	ClientSoundHandler.stopMusic();
	        } else if (ModKeybinds.nextMusicKey.isPressed()) {
	        	ClientSoundHandler.stopMusic();
	        	ClientSoundHandler.launchPlaylist();
	        } else if (ModKeybinds.previousMusicKey.isPressed()) {
	        	ClientSoundHandler.stopMusic();
	        	ClientSoundHandler.index-=2;
	        	ClientSoundHandler.launchPlaylist();
	        }

	    }
	}
	
	// TENTATIVE INFRUCTUEUSE
	/*@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
	    // GUI is closing
	    if (event.getGui() == null && ClientSoundHandler.isMusicPaused()) {
	        Minecraft.getMinecraft().addScheduledTask(() -> {
	        	System.out.println("Pause force the sounds");
	            // ClientSoundHandler.forcePause(); // forcibly re-pause music
	        });
	    }
	}*/
	
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
	    	        return instance.serializeNBT(); // very important!
	    	    }

	    	    @Override
	    	    public void deserializeNBT(NBTTagCompound nbt) {
	    	        instance.deserializeNBT(nbt);
	    	    }
	    	});
	    }
	}
	
	@SubscribeEvent
	// FIXME =?> verify this method
    public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
		EntityPlayerMP player = (EntityPlayerMP) event.player;

	    if (event.toDim == -1) {
	        NBTTagCompound data = player.getEntityData();
	        if (!data.getBoolean("chaos_has_spawned")) {
	            data.setBoolean("chaos_has_spawned", true);

	            World world = player.world;

	            if (!world.isRemote) {
	                BlockPos pos = player.getPosition().add(2, 0, 2);
	                Entity entity = EntityList.createEntityByIDFromName(
	                    new ResourceLocation(Reference.MODID, "chaos_sage"), world
	                );

	                if (entity instanceof EntityChaosSage) {
	                    entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
	                    world.spawnEntity(entity);
	                }
	            }
	        }
	    }
    }
	
	@SubscribeEvent
	public static void onCrafting(ItemCraftedEvent event) {
		for (int i = 0; i < 9; i++) {
			if (event.craftMatrix.getStackInSlot(i).getItem() == ModItems.TINKERERS_HAMMER) {
				ItemStack item = event.craftMatrix.getStackInSlot(i);
				if (item.getItemDamage() < item.getMaxDamage() - 1) {
					event.player.playSound(SoundEvents.BLOCK_ANVIL_USE, 1.0f, 1.0f);
					if (!event.player.world.isRemote) event.player.sendMessage(new TextComponentString("Recipe contains hammer, playing sound... based on damage : " + (item.getItemDamage() + 1) + "/" + item.getMaxDamage()));
				} else {
					event.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
				}
			}
		}
	}

}
