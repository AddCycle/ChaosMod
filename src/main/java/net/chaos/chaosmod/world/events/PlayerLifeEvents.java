package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.blocks.BeehiveBlock;
import net.chaos.chaosmod.client.gui.inventory.GuiInventoryExtended;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.common.capabilities.accessory.CapabilityAccessory;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModDimensions;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModMaterials;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.chaos.chaosmod.items.necklace.AllemaniteNecklace;
import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.chaos.chaosmod.items.special.TinkerersHammer;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketOpenGui;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.dimensions.TeleportUtil;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerLifeEvents {

	/**
	 * FIXME : make it teleport inside "The Hive" dim with honeycomb blocks all around, honey blocks, liquid honey, bees, and all sorts of bees like worker bee that gathers resources for you, fighter bees that can fight for you, and a queen bee boss that drops you the staff in order to control/tame the bees
	 * @param event
	 */
	@SubscribeEvent
	public static void onRightClickItem(RightClickItem event) {
		World world = event.getWorld();
		if (world.isRemote)
			return;

		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItemStack();

		if (stack.getItem() != Items.ENDER_PEARL)
			return;

		RayTraceResult ray = rayTrace(world, player, false);
		if (ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK)
			return;

		IBlockState state = world.getBlockState(ray.getBlockPos());

		if (state.getBlock() == ModBlocks.BEEHIVE) {

			event.setCanceled(true);
			event.setCancellationResult(EnumActionResult.SUCCESS);
			if (state.getValue(BeehiveBlock.AGE) == 2) {
				stack.shrink(1);
				TeleportUtil.teleportCenter(player, ModDimensions.EXPERIMENTAL.getId(), 0, 100, 0);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerGatherLiquid(RightClickBlock event) {
		World worldIn = event.getWorld();
		EntityPlayer playerIn = event.getEntityPlayer();
		ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() != Items.GLASS_BOTTLE) {
			return;
		}

		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, true);
		if (raytraceresult == null) {
			return;
		} else {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = raytraceresult.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn
						.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
					return;
				}

				if (worldIn.getBlockState(blockpos).getMaterial() == ModMaterials.HONEY) {
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ,
							SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);

					event.setCancellationResult(EnumActionResult.SUCCESS);
					event.setCanceled(true);

					ItemStack filledbottle = turnBottleIntoItem(itemstack, playerIn,
							new ItemStack(ModItems.HONEY_BOTTLE));
					playerIn.setHeldItem(event.getHand(), filledbottle);
					return;
				}
			}
		}
	}

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

			// sets his first home on join
			event.getWorld().getMinecraftServer().commandManager.executeCommand(player, "sethome spawn");
		}
	}

	@SubscribeEvent
	public static void onPlayerStepsOnCrops(FarmlandTrampleEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer) || event.getWorld().isRemote)
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

		// FIXME fix container sync ? or check if it's a mathsmod issue
		if ((event.getGui() instanceof GuiInventory) && !(event.getGui() instanceof GuiInventoryExtended)) {
			if (!player.isCreative()) {
				event.setCanceled(true);
				PacketManager.network.sendToServer(new PacketOpenGui(Reference.GUI_ACCESSORY_ID));
			}
		}
	}

	// TODO : remove ChaosSage
//	private static void syncAccessory(EntityPlayerMP player) {
//		IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
//		if (cap != null) {
////			PacketManager.network.sendTo(new PacketAccessorySync(), player);
//		}
//	}

//	@SubscribeEvent
//	// FIXME =?> fix this method or remove completely since Mod direction hasChanged
//	public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
//		EntityPlayerMP player = (EntityPlayerMP) event.player;
//
//		if (event.toDim == -1) {
//			NBTTagCompound data = player.getEntityData();
//			if (!data.getBoolean("chaos_has_spawned")) {
//				World world = player.getEntityWorld();
//				world.playerEntities.forEach(p ->
//				{
//					if (!p.getEntityData().getBoolean("chaos_has_spawned")) {
//						p.getEntityData().setBoolean("chaos_has_spawned", true);
//					}
//				});
//
//				if (!world.isRemote) {
//					BlockPos pos = player.getPosition().add(2, 0, 2);
//					Entity entity = EntityList
//							.createEntityByIDFromName(new ResourceLocation(Reference.MODID, "chaos_sage"), world);
//
//					if (entity instanceof EntityChaosSage) {
//						entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
//						world.spawnEntity(entity);
//					}
//				}
//			}
//		}
//	}

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

	public static RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
		float f = playerIn.rotationPitch;
		float f1 = playerIn.rotationYaw;
		double d0 = playerIn.posX;
		double d1 = playerIn.posY + (double) playerIn.getEyeHeight();
		double d2 = playerIn.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
		Vec3d vec3d1 = vec3d.addVector((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	}

	public static ItemStack turnBottleIntoItem(ItemStack bottlestack, EntityPlayer player, ItemStack stack) {
		bottlestack.shrink(1);

		if (bottlestack.isEmpty()) {
			return stack;
		} else {
			if (!player.inventory.addItemStackToInventory(stack)) {
				player.dropItem(stack, false);
			}

			return bottlestack;
		}
	}
}