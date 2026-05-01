# Changelog 1.0.16

TODO (entities):
  - make bee AI and behavior towards beehive & also make a pollen mechanic

TODO (Structures):
  - make an abstract/reusable Iworldgenerator for generating structures
  - fix: stairs part head hit
  - I think everything is done except avoiding the vanilla generated structures (TODO: but I think it's already handled by forge salt system (maybe query other mods random in order to avoid them via intercommunicationevent ?))
  - Also add a button/mode to the jigsaw to generate along with maxDepth levels
  - todo: custom loot_tables (todo, do more with better loot) + make a custom chest in order to spawn loot with an effect on opened inside the structure (at boss death (more like a custom entity with animations + explosion))
  - todo: rework the boss altar to like hold the NBT data of the boss/entity
  - todo: make boss_room blocks to enclose the exit when you summon/enter the boss room maybe with unbreakable blocks until the boss is killed or every player is dead (make them spectator (or just put a bedroom nearby the boss_room))
  - Add variants to gallions and random loots (exemple a wooden bark, then a bigger ship, then a gallion with loots and pirates enemies (gives a challenge with spawners (vikings/pirates)) to prevent players from looting too easily) (good to start structures)

TODO (Build):
  - (complex) add a `//copy` based on vanilla `/clone` (maybe because I might recode it in order to be compatible with `//undo` & `//redo` commands) command with completions
  - (registry making it complex) make the `//paste <x> <y> <z> [registry (int)]` take from it (works finally with //undo & //redo)
  - make a ruler item to measure distances between pos1 & pos2
  - make noise values
  - add axis rendering x red, z blue, y green (like structure block does)

TODO (WorldGen):
  - priority: add animals & mobs inside the `dense_forest` biome
  - bear: armors support (todo: gold, leather, diamond)
  - vulture (done), detail: make it hybrid -> ground also (like chicken) to make interaction (later maybe)
  - Rework biomes (ender_garden, chaosland_biome, hell_biome) or completely remove them for other biome types
  - Removed the boss altars in order to put them inside the later-made structures (do not forget before release)
  - (advanced) add underground (biomes or caves) with vines and decoration and more minerals
  - (advanced) make a sky island biome
  - (advanced) make a magma/fire biome with fire creatures
  - animals ideas: kangaroos (magma cube jump animations to look for), monkey, beaver, pheonix, hog
  - make a prehistorical biome with dinosaurs
  - make like a `spring/summer` biome with yellowish-colors [in_progress...]

TODO (JOBS):
  - Make a separate button that leads to the sharedTasks display with ranking & other things inchaa'Allah
  (POLISH)
  - add scoreboard of current shared task (polish the design of the gui)
  - Make a button the claim global task reward so if players are offline they still get the reward [!!!! REALLY NEEDED !!!!]

  - Feat: `/jobs` command needs resetting tasks individually or setting individual tasks completion for admins + testing
  - Make jobs exclusive item rewards for FISHERMAN, TAMER, TRAVELER (`cobblestone_void` should be for miner exclusively maybe (like uncraftable otherwise or maybe just unlocking its craft))

  - Add bedrock bridging feature [TODO] (for travelers I think)
  - add a spelunkery table which requires translation like (alex's cave mod) (travelers I think to find coords of the structures or buried treasures (traveler job))
  - Add `traveler` job task `structures` discovery later... (using isInsideStructure method inside the generation)
  - allow `/locate` to find custom structures added by mathsmod & chaosmod
  - Make a new quest system kinda like `FTB Quests Mod` were you follow a roadmap (more like a separate mod/api)
  - Add a pin task button to make it on the scoreboard (if not too many inside the screen like a todo list)
  - Add UI toast upon task completion
  - Get rid of patchouli API to make my own information book (might take some time but more reliable since it doesn't require any dependencies)
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward for jobs/market) or cut picsou entity for a better one (idea: racoon that sneaks into bases & steals from players chests)

TODO (Bosses):
  - 1: add a biome
  - 2: add a mob (ex: magma biome with magma_bears) that has special loot
  - 3: add a boss that spawns requires the special loot
  - 4: replicate and add variety
  - 5: they loot a good weapon or an armor piece that does something more (or just quest reward (every player gets it))

TODO (rework):
  - make the items upgrades instead of crafting others: oxonium -> forge -> allemanite -> forge -> enderite -> forge -> solarite and so on...
  - make the swords block attacks by holding right-click wielding them (pre-1.9 pvp)
  - remake armors (enderite_texture: more like a crying obsidian or maybe just remove it and go crying_obsidian_armor) + give them effects unique (variety)
  - remake allemanite_backpack and make it upgradable with more storage and actually syncs to not loose loot (verify/re-implement)

IDEAS:
  - make the end or nether disabled until a specific accomplishement is reached (I don't think it's a good idea because it means that mathsmod features in the nether won't be usable until then slowing progress)
  - Add common tasks for all players to do but still competition and gui who farmed the most in each task and rewards accordingly (visual effects too like ranking stats and progress bars vertical animated for satisfaction)
  - Add an exclusive item, player tracking compass or biome tracking compass (for the hunt or exclusive to jobs)
  - Wheat boss like a scarecrow
  - Custom event only-tipped-arrows allowed
  - InventorySorter: like a little item that when you shift-right-click on, it sorts a container based on names/ids/types... depends on the mode that he's on
  - Also make the items have a rarity/tier so that you can basically reforge everything.
  - Rework the current bosses and entities models (add effects + animations) [DOING]
  - Skill tree
  - add buried treasure with a minimap
  - Herobrine, Putin, Technoblade, DaquavisMC, Dream boss
  - Structure loot chest that when opened summons entities that will fight to protect the chest
  - Structures matching the biomes starting with vanilla ones for instance : desert = pyramid, plain = pillagerTower
  - Cook (like with stats increasing if you have a good alimentation)
  - Add new lava fish inside the nether with a custom fishing rod too (bober entity fireResistant)
  - Sheepwars
  - Don't forget aerial structures as long as we have the jetpack + elytras(fireworks) (TODO : make it a task like use elytras with tier3 fireworks (traveler)))
  - add variants to animals like skins and other models, no behavior, just reskin variety
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
  - Add SFX to jobs upon level up (TODO) (useless right now as long as every task completed is a level up
  - bears - detail: have something in their mouth sometimes

PLAYTEST:
  - All jobs task completion + granting rewards correctly (client+server)

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
  - Fun: biomes are kinda redundant, fix: add new custom decoration blocks, structures, mobs, tameable animals, each one being exclusive to the biome to match the color/style of each 4 biomes and make them different [DOING]
  - Fix: `oxonium_slab` order bottom/top order (on compiled versions (might be caused by lighting or mathsmod too (most probable cause: flag 2|16 or mathsmod:hunter entity)))
  - EntityViking clearing inventory on right-click and kills you...
  - DrawerTESR: item icon lights even in the dark + container overflow issues (code issue)
  - Sword Of Wrath orientation towards monster attacked (fix animations)
  - Cascading worldgen lag for generated structures... (seems fixed for now I will let it here as I will add more huge structures later)
  - Fix: bear canSpawnHere needs to check the AABB before (just override method)
  - make my own main menu gui in order to disable it from the mod's config

REFACTOR (when needed):
  - entity package
  - DimensionWarpCommand

OPTIMIZATION:
  - Biome gen rework a little bit slower than before, I Might as well remove the biome flower system and come back to IWorldGenerator gen (less overhead)

TODO:
  - Make a tool for making easier animations in blockbench directly (plugin or else)
  - Make another tool for DjoCaillou to mod without coding (if not, make tutorials on MCreator for 1.12.2)

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
  - feat: added 3 `taunts`
  - feat: added `dense_forest` biome
  - feat: added `bear` spawn with variants (tameable with `honey_bottle`(s), healing with `fish` & having various `sitting_poses`)
  - feat: jobs `tasks_progress` are now shared between players for competition
  - feat: `//wand`, `//fill <block> <meta> [parameters]`, `//undo`, `//redo`, `//randomize <type> <block>:<meta>:<weight> ...`, `//paste <x> <y> <z>` commands to help build structures (creative only) + visuals
  - feat: added overlay infos with icons & fixed the blocks displayName
  - feat: added `jigsaw_block` from 1.14 ported to 1.12.2 (the superior version)
  - feat: added `/jigsaw <structure_start> <x> <y> <z> <depth>` command placing procedural structure
  - feat: added `bee`

Changes:
  - feat: `oxonium_pickaxe` has `vein miner` mode compatible with other mods `ores`
  - tweak: `custom_bricks` minerals `oxonium`, `allemanite`, `enderite` craft changed from `stonebricks` to `brick_block`
  - fix: Pixou drops the stole `mineral` blocks (I think only gold works right now)
  - chore: Removed garbage blocks as andesite, diorite, granite from generation
  - perf: optimized `/find <block_id> [range]` command
  - refactor: automatic packet registration with custom annotation `@ModPackets(modid, side)`
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
  - fix: `jobs` progress cleared when traveling between dimensions
  - style: `oxonium_sword` texture back to normal (short_sword)

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
