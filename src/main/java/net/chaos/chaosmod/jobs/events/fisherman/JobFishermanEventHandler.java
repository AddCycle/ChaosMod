package net.chaos.chaosmod.jobs.events.fisherman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBiomes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Biomes;
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

	    LootEntry vineEntry = new LootEntryTable(
	        new ResourceLocation(Reference.MODID, "inject/gameplay/jungle/vine_fish"),
	        50, 1,
	        new LootCondition[]{ jungleCondition },
	        "chaosmod:vine_fish"
	    );

	    LootEntry iglooEntry = new LootEntryTable(
	        new ResourceLocation(Reference.MODID, "inject/gameplay/snowy/igloo_fish"),
	        50, 1,
	        new LootCondition[]{ iglooCondition },
	        "chaosmod:igloo_fish"
	    );

	    LootPool fishingPool = new LootPool(
	        new LootEntry[]{ vineEntry, iglooEntry },
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

	@SubscribeEvent
	public static void onPlayerFishing(ItemFishedEvent event) {
		if (event.getEntityPlayer().getEntityWorld().isRemote)
			return;
	}
}