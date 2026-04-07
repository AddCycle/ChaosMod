package net.chaos.chaosmod.jobs.events.fisherman;

import static net.chaos.chaosmod.jobs.events.JobEventUtils.onItemFished;
import static net.chaos.chaosmod.jobs.events.JobEventUtils.prefixId;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.items.food.fish.CustomFishFood;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import util.Reference;

// FIXME : fix data
// FIXME : fix the jsons conditions
@EventBusSubscriber(modid = Reference.MODID)
public class JobFishermanEventHandler {
	private static Set<EntityPlayer> guiOpen = new HashSet<>();
	private static Map<EntityPlayer, Integer> delay = new HashMap<>();

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side.isClient()) return;

		EntityPlayer player = event.player;

		if (player.fishEntity != null && player.fishEntity.isInWater()) {
			if (guiOpen.contains(player)) return;

			if (!delay.containsKey(player)) {
				delay.put(player, 20);
				return;
			}

			int t = delay.get(player) - 1;

			if (t <= 0) {
	            player.openGui(Main.instance, Reference.GUI_FISHINGGAME_ID,
	                    player.world,
	                    (int) player.posX,
	                    (int) player.posY,
	                    (int) player.posZ);

				delay.remove(player);
	            guiOpen.add(player);
			} else {
				delay.put(player, t);
			}
		} else {
			delay.remove(player);
			guiOpen.remove(player);
		}
	}
	
	@SubscribeEvent
	public static void onLootTableLoadEvent(LootTableLoadEvent event) {
		if (!event.getName().equals(LootTableList.GAMEPLAY_FISHING)) return;

	    LootCondition jungleCondition = new LootCondition() {
	        @Override
	        public boolean testCondition(Random rand, LootContext context) {
	        	EntityPlayer player = getPlayerFromContext(context);
	            if (player == null) return false;
	            Biome biome = context.getWorld().getBiome(player.getPosition());
	            
	            boolean result = biome == Biomes.JUNGLE;
	            Main.getLogger().info("[ChaosMod] jungleCondition checked — biome: {}, result: {}", biome.getBiomeName(), result);
	            return result;
	        }
	    };

	    LootCondition iglooCondition = new LootCondition() {
	        @Override
	        public boolean testCondition(Random rand, LootContext context) {
	        	EntityPlayer player = getPlayerFromContext(context);
	            if (player == null) return false;
	            Biome biome = context.getWorld().getBiome(player.getPosition());

	            boolean result = biome == ModBiomes.GIANT_MOUNTAIN;
	            Main.getLogger().info("[ChaosMod] iglooCondition checked — biome: {}, result: {}", biome.getBiomeName(), result);
	            return result;
	        }
	    };

	    LootCondition desertCondition = new LootCondition() {
	        @Override
	        public boolean testCondition(Random rand, LootContext context) {
	        	EntityPlayer player = getPlayerFromContext(context);
	            if (player == null) return false;
	            Biome biome = context.getWorld().getBiome(player.getPosition());

	            boolean result = biome == Biomes.DESERT;
	            Main.getLogger().info("[ChaosMod] desertCondition checked — biome: {}, result: {}", biome.getBiomeName(), result);
	            return result;
	        }
	    };

	    LootEntry vineEntry = new LootEntryTable(
	        new ResourceLocation(Reference.MODID, "inject/gameplay/jungle/vine_fish"),
	        30, 1,
	        new LootCondition[]{ jungleCondition },
	        "chaosmod:vine_fish"
	    );

	    LootEntry iglooEntry = new LootEntryTable(
	        new ResourceLocation(Reference.MODID, "inject/gameplay/snowy/igloo_fish"),
	        35, 1,
	        new LootCondition[]{ iglooCondition },
	        "chaosmod:igloo_fish"
	    );

	    LootEntry desertEntry = new LootEntryTable(
	        new ResourceLocation(Reference.MODID, "inject/gameplay/desert/desert_fish"),
	        35, 1,
	        new LootCondition[]{ desertCondition },
	        "chaosmod:desert_fish"
	    );

	    LootPool fishingPool = new LootPool(
	        new LootEntry[]{ vineEntry, iglooEntry, desertEntry },
	        new LootCondition[0],       // no pool-level condition
	        new RandomValueRange(2f, 4f),
	        new RandomValueRange(0f),
	        "custom_main"
	    );

	    event.getTable().addPool(fishingPool);
	}
	
	private static EntityPlayer getPlayerFromContext(LootContext context) {
	    Entity looted = context.getLootedEntity();
	    if (looted instanceof EntityPlayer) {
	        return (EntityPlayer) looted;
	    }
	    if (looted instanceof EntityFishHook) {
	        return ((EntityFishHook) looted).getAngler();
	    }
	    return null;
	}

	// FIXME : use json fields instead
	private static final ResourceLocation IGLOO_FISH = new ResourceLocation(Reference.MODID, "igloo_fish");
	private static final ResourceLocation VINE_FISH = new ResourceLocation(Reference.MODID, "vine_fish");
	private static final ResourceLocation DESERT_FISH = new ResourceLocation(Reference.MODID, "desert_fish");
	private static final ResourceLocation ENDER_FISH = new ResourceLocation(Reference.MATHSMOD, "ender_fish");

	@SubscribeEvent
	public static void onPlayerFishing(ItemFishedEvent event) {
		if (event.getEntityPlayer().getEntityWorld().isRemote)
			return;
		
		Predicate<ItemStack> vanillaFish = stack -> stack.getItem() instanceof ItemFishFood;
		Predicate<ItemStack> customFish = stack -> stack.getItem() instanceof CustomFishFood;
		Predicate<ItemStack> anyFish = stack -> vanillaFish.or(customFish).test(stack);

		onItemFished(event, anyFish, (items, count) -> {
			incrementTask("first_catch", event.getEntityPlayer(), count);
		});

		onItemFished(event, IGLOO_FISH , (items, count) -> {
			incrementTask("cold_catch", event.getEntityPlayer(), count);
		});

		onItemFished(event, VINE_FISH, (items, count) -> {
			incrementTask("jungle_catch", event.getEntityPlayer(), count);
		});

		onItemFished(event, DESERT_FISH, (items, count) -> {
			incrementTask("desert_catch", event.getEntityPlayer(), count);
		});

		onItemFished(event, ENDER_FISH, (items, count) -> {
			incrementTask("ender_catch", event.getEntityPlayer(), count);
		});
	}

	private static void incrementTask(String taskId, EntityPlayer player, int amount) {
		JobEventUtils.incrementTask((EntityPlayerMP) player, prefixId("fisherman"), prefixId(taskId), amount);
	}
}