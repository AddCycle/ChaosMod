package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.client.gui.inventory.GuiInventoryExtended;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.common.capabilities.accessory.CapabilityAccessory;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.chaos.chaosmod.items.necklace.AllemaniteNecklace;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.items.special.TinkerersHammer;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketOpenGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import vazkii.patchouli.api.PatchouliAPI;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerLifeEvents {

	@SubscribeEvent
	public static void onPlayerJoin(EntityJoinWorldEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer) || event.getWorld().isRemote)
			return;

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

			if (Loader.isModLoaded(Reference.PATCHOULI)) {
				givePlayerInstructionBook(player);
			}

			// sets his first home on join
			event.getWorld().getMinecraftServer().commandManager.executeCommand(player, "sethome spawn");
		}
	}

	@SubscribeEvent
	public static void onPlayerStepsOnCrops(FarmlandTrampleEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer))
			return;
		if (event.getWorld().isRemote)
			return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ModItems.OXONIUM_BOOTS) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
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

		IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
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
	public static void onGuiOpened(GuiOpenEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		if ((event.getGui() instanceof GuiInventory) && !(event.getGui() instanceof GuiInventoryExtended)) {
			if (!player.isCreative()) {
				event.setCanceled(true);
				PacketManager.network.sendToServer(new PacketOpenGui(Reference.GUI_ACCESSORY_ID));
			}
		}
	}

	@SubscribeEvent
	// FIXME =?> fix this method or remove completely since Mod direction hasChanged
	public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
		EntityPlayerMP player = (EntityPlayerMP) event.player;

		if (event.toDim == -1) {
			NBTTagCompound data = player.getEntityData();
			if (!data.getBoolean("chaos_has_spawned")) {
				World world = player.getEntityWorld();
				world.playerEntities.forEach(p ->
				{
					if (!p.getEntityData().getBoolean("chaos_has_spawned")) {
						p.getEntityData().setBoolean("chaos_has_spawned", true);
					}
				});

				if (!world.isRemote) {
					BlockPos pos = player.getPosition().add(2, 0, 2);
					Entity entity = EntityList
							.createEntityByIDFromName(new ResourceLocation(Reference.MODID, "chaos_sage"), world);

					if (entity instanceof EntityChaosSage) {
						entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
						world.spawnEntity(entity);
					}
				}
			}
		}
	}

	@SubscribeEvent
	// TODO : make the pitch change based on durability
	public static void onCrafting(ItemCraftedEvent event) {
		if (event.player.world.isRemote)
			return;

		EntityPlayer player = event.player;

		for (int i = 0; i < 9; i++) {
			if (event.craftMatrix.getStackInSlot(i).getItem() == ModItems.TINKERERS_HAMMER) {
				ItemStack item = event.craftMatrix.getStackInSlot(i);
				if (item.getItemDamage() < item.getMaxDamage() - 1) {
					player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_USE,
							SoundCategory.PLAYERS, 1.0f, 1.0f);
					player.sendMessage(
							new TextComponentString("Recipe contains hammer, playing sound... based on damage : "
									+ (item.getItemDamage() + 1) + "/" + item.getMaxDamage()));
				} else {
					player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ANVIL_LAND,
							SoundCategory.PLAYERS, 1.0f, 1.0f);
				}
			}
		}
	}

	public static void givePlayerInstructionBook(EntityPlayer player) {
		ItemStack book = PatchouliAPI.instance.getBookStack(Reference.PREFIX + "chaos_almanac");
		player.addItemStackToInventory(book);
	}
}