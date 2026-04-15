# Changelog 1.0.16

TODO (JOBS):
  - Make a button the claim global task reward so if players are offline they still gets the reward
  - Make all the jobs tasks & rewards from level 1 -> 5 : ALCHEMIST, TRAVELER
  - Feat: `/jobs` command needs resetting tasks individually or setting individual tasks completion for admins
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DONE 3 just make the remaining tasks upwards] [DOING]
  - Make jobs exclusive item rewards for FISHERMAN, TAMER, TRAVELER (`cobblestone_void` should be for miner exclusively maybe (like uncraftable otherwise or maybe just unlocking its craft))
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward for jobs/market)
  - Drop the ChaosMod `advancements` for a new quest system kinda like `FTB Quests Mod` were you follow a roadmap (more like a separate mod/api)
  - Add a scoreboard of the most leveled up players per job ranking gui (individual + for common tasks)
  - Add a pin task button to make it on the scoreboard (if not too many inside the screen like a todo list)
  - Add SFX to jobs upon level up (TODO)
  - Add UI toast upon task completion
  - Look at structurecomponentendtemplate
  - Add variants to gallions and random loots (exemple a wooden bark, then a bigger ship, then a gallion with loots and pirates enemies (gives a challenge with spawners) to prevent players from looting too easily) (good to start structures)
  - Add bedrock bridging feature [TODO] or bridge placer (DONE) (for travelers I think)
  - Add `traveler` job task `structures` discovery later...
  - allow `/locate` to find custom structures added by mathsmod & chaosmod
  - Attack world generation in a dimension to train then the overworld, first try to make advanced trees then we'll talk...
  - Get rid of patchouli API to make my own information book (might take some time but more reliable since it doesn't require any dependencies)

PLAYTEST:
  - All jobs task completion + granting rewards correctly (client+server)

IDEAS:
  - Add common tasks for all players to do but still competition and gui who farmed the most in each task and rewards accordingly (visual effects too like ranking stats and progress bars vertical animated for satisfaction)
  - Add an exclusive item, player tracking compass or biome tracking compass (for the hunt or exclusive to jobs)
  - Wheat boss like a scarecrow
  - Custom event only-tipped-arrows allowed
  - InventorySorter: like a little item that when you shift-right-click on, it sorts a container based on names/ids/types... depends on the mode that he's on
  - Also make the items have a rarity/tier so that you can basically reforge everything.
  - Rework the current bosses and entities models (add effects + animations)
  - Skill tree
  - add buried treasure with a minimap
  - Herobrine, Poutine, Technoblade, DaquavisMC, Dream boss
  - Structure loot chest that when opened summons entities that will fight to protect the chest
  - Structures matching the biomes starting with vanilla ones for instance : desert = pyramid, plain = 
  - Cook (like with stats increasing if you have a good alimentation)
  - Add new lava fish inside the nether with a custom fishing rod too
  - Sheepwars
  - Don't forget aerial structures as long as we have the jetpack + elytras(fireworks) (TODO : make it a task like use elytras with tier3 fireworks (traveler)))
MINOR:
  - add a visual effect to gamemode switch with hotkeys F3+F4
  - add a custom cursor while ingame menus
  - add an entirely customizable gui notes (armor equipped and durability on screen (position, scale, ... everything customizable) like a custom client would do (config disabling feature))

REWORK(funnier to grind):
  1- Jobs might be boring loops like : `do X -> get EXP` doesn't seem interesting enough
  Instead, I can do some `missions/challenges` like `mine 10 iron without placing torches` ==> more engaging ### needs player restriction implementation (capability) ###
  2- Add random `events` like rare fish appeared to fight and then when killed grants more EXP or treasure spot nearby
  3- Let's player choose : pick tasks like a easy/hard one : risky job, or safe one
  4- Add progression effect : for example when lvl 5 -> faster fishing, chance to double ores, lifesteal chance/crits... in each job
  5- Add combo/streak system : catch fish in a row, mine ores without dying -> bonus (encourages skill)
  6- Mini-goals inside tasks: `mine 50 iron` becomes `find cave`, `mine iron`, ...
  7- Special contracts (limited-events): legendary fish appeared at night (making him glow at night)
  8- Add `visual world` effects:

POLISH:
  - Shared tasks gui make it in the scoreboard pinnable maybe or just make a visual indicator in the gui
  - additionaloverlayinfos : health+armor icons drawing instead of raw values

TODO (next version) ?:
  - Jobs : add a randomizer to job_tasks to remove the impression of doing the same tasks in all the survivals (later when more tasks like on 10 tasks make 5 only)
  - Jobs: tweak or change to make it funnier according to `REWORK` section
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too (inspire from end cities jigsaw model)
  - Hunt (or custom pvp events which can be started with commands too but saves everything the player was doing before (position+dimension, stuff))
  - Market (let players sell and buy items and see the evolution of the price inside the market with some graphs)
  - Fix playlist and soundMgr system (need to reimplement everything)
  - add `magnet` + `sword_of_wrath` to structures loots or jobs exclusive rewards
  - SwordOfWrath needs to target the entity if the player hits it

ISSUES:
  - Fun: biomes are kinda redundant, fix: add new custom decoration blocks, structures, mobs, tameable animals, each one being exclusive to the biome to match the color/style of each 4 biomes and make them different
  - Fix: `oxonium_slab` order bottom/top order (on compiled versions (might be caused by lighting or mathsmod too))
  - EntityViking clearing inventory on right-click and kills you...
  - DrawerTESR: item icon lights even in the dark + container overflow issues (code issue)
  - Sword Of Wrath orientation towards monster attacked (fix animations)
  - Cascading worldgen lag for generated structures... (seems fixed for now I will let it here as I will add more huge structures later)

REFACTOR (need):
  - entity package
  - DimensionWarpCommand

OPTIMIZATION:
  - Biome gen rework a little bit slower than before, I Might as well remove the biome flower system and come back to IWorldGenerator gen (less overhead)

New:
  - MainMenu custom `LOGO` (overrides `minecraft.png`)
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`
  - added `visited biomes` capability && `/biomes` command
  - Fishing Mechanic where timing is the key to catch something (Undertale inspired thx `Toby Fox`)
  - ChaosMasterBoss in the `custom_dim` deals `blue_fire_damage` with a new overlay effect
  - feat: fishing gui minigame instead of boring vanilla fishing
  - feat: `jobs` grants you `block/item` reward leveling up
  - feat: added `magnet` item that pickups nearby loot
  - feat: added a quick way to change gamemode [hotkey F3+F4]
  - feat: add `super_totem` item that triggers even when isn't held in hands
  - style: rendering saturation in HUD
  - decoration: added `tree barks`, `flower_patches`
  - feat: `right-click` crops to harvest instead of destroying them
  - feat: add `igloo_fish`, `vine_fish`, `desert_fish` exclusive to cold, jungle, desert biomes respectively
  - feat: add `dirty_water` fluid in `desert` biome
  - feat: add `shared_tasks` with common progress (communism)
  - feat: `fertilized_water` works with mathsmod flowers & colorized with green
  - feat: `/locate` command replaced sending a TELEPORT message to the structure location

Changes:
  - feat: `oxonium_pickaxe` has `vein miner` mode compatible with other mods `ores`
  - tweak: `custom_bricks` minerals `oxonium`, `allemanite`, `enderite` craft changed from `stonebricks` to `brick_block`
  - fix: Pixou drops the stole `mineral` blocks (I think only gold works right now)
  - chore: Removed garbage blocks as andesite, diorite, granite from generation
  - perf: optimized `/find <block_id> [range]` command
  - refactor: automatic packet registration with custom annotation @ModPackets(modid, side)
  - fix: custom flowers drops the correct variant when the block below is broken
  - feat: every player is able to see the summoning boss `cutscene`
  - feat: `final credits` displayed after beating `ChaosMaster Boss`
  - feat: `Minimap` should render the correct biome color for : foliage, grass or (water: prioritizes the mapcolor over it so it's not working)
  - style: tree logs are breaking sequentially with `oxonium_axe`
  - style: vein miner progressive destroy blocks too
  - tweak: `OxoniumVillages` have only 40% to generate over vanilla `Village`
  - fix: bosses loots cannot burn anymore
  - feat(endgame): `sword_of_wrath` entity/item -> follows the owner around and kills mobs for him
  - fix: `viking_gallions` are now only generated in watery-biomes like `ocean`
  - fix: `fertilized_water` now waits 20secs to start fertilize crops (making it unspammable)
  - balance: `custom_leaves` dropping too many `golden_apple` on decay

MODEL-ISSUES:
Caused by: java.io.FileNotFoundException: chaosmod:models/item/atm_block.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
Caused by: java.lang.RuntimeException: Encountered an exception when loading model definition of model chaosmod:blockstates/atm_block.json
	at net.minecraft.client.renderer.block.model.ModelBakery.loadMultipartMBD(ModelBakery.java:228) ~[ModelBakery.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.getModelBlockDefinition(ModelBakery.java:208) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:265) ~[ModelLoader.class:?]
	... 20 more
Caused by: java.io.FileNotFoundException: chaosmod:blockstates/atm_block.json
	at net.minecraft.client.resources.FallbackResourceManager.getAllResources(FallbackResourceManager.java:104) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getAllResources(SimpleReloadableResourceManager.java:79) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadMultipartMBD(ModelBakery.java:221) ~[ModelBakery.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.getModelBlockDefinition(ModelBakery.java:208) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:265) ~[ModelLoader.class:?]
	... 20 more
Caused by: java.io.FileNotFoundException: chaosmod:models/item/portal_frame.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 21 more
Caused by: java.lang.RuntimeException: Encountered an exception when loading model definition of model chaosmod:blockstates/chaosland_portal.json
	at net.minecraft.client.renderer.block.model.ModelBakery.loadMultipartMBD(ModelBakery.java:228) ~[ModelBakery.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.getModelBlockDefinition(ModelBakery.java:208) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:265) ~[ModelLoader.class:?]
	... 20 more
Caused by: java.io.FileNotFoundException: chaosmod:blockstates/chaosland_portal.json
	at net.minecraft.client.resources.FallbackResourceManager.getAllResources(FallbackResourceManager.java:104) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getAllResources(SimpleReloadableResourceManager.java:79) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadMultipartMBD(ModelBakery.java:221) ~[ModelBakery.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.getModelBlockDefinition(ModelBakery.java:208) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:265) ~[ModelLoader.class:?]
	... 20 more
[18:49:56] [Client thread/FATAL] [FML]: Suppressed additional 3 model loading errors for domain chaosmod

Test without AT classes then show to Louis maybe not use coremod : useless
Yep no need for it test it then :

Rename AT file to mathsmod_at.cfg in src/main/resources/
Add rename '(.+_at.cfg)', 'META-INF/$1' to processResources
Add useDepAts = true to the minecraft {} block
Correct FMLAT in the jar manifest
Run setupDecompWorkspace to reprocess everything
Or clean tasks cleanCacheTasks eclipse task bref (everything...)
STEPS : accesstransformers :

1 : build.gradle

Inside processResources put : 

Inside jar : modid_at.cfg
jar {
    manifest {
        attributes([
            "FMLAT": "chaosmod_at.cfg"
        ])
    }
}

Inside minecraft :
useDepAts = true
