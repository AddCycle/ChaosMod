package net.chaos.chaosmod.world.events;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.client.inventory.shield.ShieldImpl;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.chaos.chaosmod.items.necklace.AllemaniteNecklace;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.items.special.TinkerersHammer;
import net.chaos.chaosmod.jobs.GuiScreenJobs;
import net.chaos.chaosmod.network.PacketManager;
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
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import vazkii.patchouli.api.PatchouliAPI;

@EventBusSubscriber
public class PlayerLifeEvents {
	
	@SubscribeEvent
	// FIXED
	public void onPlayerJoin(EntityJoinWorldEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer)) return; // only if the entity is a player
		if (event.getWorld().isRemote) return; // running only server side

		EntityPlayer player = (EntityPlayer) event.getEntity();

		String key = Reference.PREFIX + "first_join";

		NBTTagCompound entityData = player.getEntityData();
		NBTTagCompound persistentData;
		if (entityData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) { // 10 = compound type
		    persistentData = entityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		} else {
		    persistentData = new NBTTagCompound();
		    entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistentData);
		}

		boolean firstJoin = !persistentData.hasKey(key);

		if (firstJoin) {
			persistentData.setBoolean(key, true);
			entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistentData);

			if (Loader.isModLoaded("patchouli")) {
				givePlayerInstructionBook(player);
			}

			// sets his first home on join
			event.getWorld().getMinecraftServer().commandManager.executeCommand(player, "sethome spawn");
		}
	}
	
	@SubscribeEvent
	public void onPlayerStepsOnCrops(FarmlandTrampleEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer)) return;
		if (event.getWorld().isRemote) return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.OXONIUM_BOOTS) {
			event.setCanceled(true);
		}
	}

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
		if (cap != null) {
			ItemStack accessory = cap.getAccessoryItem();
			if (!accessory.isEmpty()) {
				if (accessory.getItem() instanceof OxoniumNecklace) {
					if (!player.isPotionActive(MobEffects.REGENERATION)) {
						player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 * 3, 0, false, false));
					}
				} else if (accessory.getItem() instanceof AllemaniteNecklace) {
					if (!player.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
						player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20 * 3, 0, false, false));
					}
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
				PacketManager.network.sendToServer(new PacketOpenAccessoryGui());
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
			} else if (ModKeybinds.displayJobsKey.isPressed()) {
				mc.displayGuiScreen(new GuiScreenJobs(null));
			}
		}
	}

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(Reference.MODID, "accessory"), new ICapabilitySerializable<NBTTagCompound>() {
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
				World world = player.getEntityWorld();
				world.playerEntities.forEach(p -> {
					if (!p.getEntityData().getBoolean("chaos_has_spawned")) {
						p.getEntityData().setBoolean("chaos_has_spawned", true);
					}
				});


				if (!world.isRemote) {
					BlockPos pos = player.getPosition().add(2, 0, 2);
					Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(Reference.MODID, "chaos_sage"), world);

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

	@SubscribeEvent
	public void attachCapabilityShield(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(Reference.MODID, "shield"), new ICapabilitySerializable<NBTTagCompound>() {
				final ShieldImpl instance = new ShieldImpl();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == ModCapabilities.SHIELD;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == ModCapabilities.SHIELD ? ModCapabilities.SHIELD.cast(instance) : null;
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
	
	public void givePlayerInstructionBook(EntityPlayer player) {
		ItemStack book = PatchouliAPI.instance.getBookStack(Reference.PREFIX + "chaos_almanac");
		player.addItemStackToInventory(book);
	}
}
