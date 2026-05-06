# Changelog 1.0.16

This should be a huge scope and surely enough for 1.0.16 ver.

TODO (items):
 - player_tracking_compass (seems done only texture remains) [TODO: make crafts expensive]
 - biome_tracking_compass [DONE only texture remains (might crash server-side (that's why playtest needed))] [TODO : make crafts expensive]
 - structure_locator_compass [DO IT ONLY WHEN OTHER STRUCTURES AND WORLDGENLAG issues are fixed] [TODO: don't make craft too expensive]
 - wings (model & rendering might be the hardest part)

TODO (armors):
 - bear_armors: maybe make a separate item for it or just let that be

TODO (blocks):
 - pollenizedFlowers (multiple variants)

TODO (tileentities(te)):
 - advanced/enhanced enchanting_table

TODO (entities):
 - bee (behavior AI + pollen logic)
 - chest(dungeon_reward)
 - bumblebee
 - honeyCow
 - honeyWolf
 - honeySlimes
 - honeyBears
 LATER
 - vulture

TODO (bosses):
 - Queen Bee
 - 4 bosses to rework (put altars inside later structures)

TODO (dimensions):
 - "The HIVE"

TODO (WorldGen):
 - "The HIVE"

TODO (structures):
 - (chest)entity dungeon_reward
 - worldgenerator
 ISSUES:
 - avoiding vanilla structures (with salt) (last time happened intersection with mineshaft)
 LATER:
 - maybe query other mods random in order to avoid them via intercommunicationevent(?)
 - Also add a button/mode to the jigsaw to generate along with maxDepth levels (when friends will make structures as well only)
 - when more items gets added at the end of update: add them for better loot_tables
 - rework the boss altar to like hold the NBT data of the boss/entity
 - make boss_room blocks to enclose the exit when you summon/enter the boss room maybe with unbreakable blocks until the boss is killed or every player is dead (make them spectator (or just put a bedroom nearby the boss_room))
 - Add variants to gallions and random loots (exemple a wooden bark, then a bigger ship, then a gallion with loots and pirates enemies (gives a challenge with spawners (vikings/pirates)) to prevent players from looting too easily) (good for variety inside oceans)

SCOPE:

 items:
  - player_tracking_compass: direction + distance (look for vanilla compass code) [seems DONE - only texture remains]
  - biome_tracking_compass: direction + distance (look for vanilla compass code)(OR just coords for the start) [needs gui & choosing biome]
  - structure_locator_compass: direction + distance (look for vanilla compass code)(OR just coords for the start) [DO IT ONLY WHEN MORE STRUCTURES ARE DONE + cascadingWorldGenLag fixed]
  - wings: model -> texture -> wear it -> double-jump (seems quite hard and long to have something pleasant/good-looking)

 armors: DONE

 te:
  - enchanting_table: model -> block -> te -> renderer -> gui -> container -> polish with effects (books flying, particles) -> crystals or another resource minable to make enhanced caves (amethyst or budding amethyst or red-diamond mineral (new ones TODO: move to IDEAS) (charged(?))) -> choose enchantements cost 1 mineral [needs some steps beforehand + gui, might take some time]

 dims:
  - "The Hive"
   - items: honeycomb
   - blocks: honeycombBlock
   - entities: honeyCow, honeyWolf, honeySlimes, honeyBears
   - boss: Queen Bee
   - WorldGen:
    - honeycomb blocks all around (main one)
    - honey blocks (underneath sometimes)
    - liquid honey (underneath forming like lakes)
    (advanced TODO : move to ideas)
    - bees
    - all sorts of bees like:
    - Worker bee (gathers resources for you)
    - Fighter bees (fight for you)
    - Queen bee boss (drops you the staff in order to control/tame bees)

 entities:
  - bee: AI and behavior towards beehive (go in and out of their hives) & also make a pollen mechanic (where they carry a certain amount AI flowers visited) (cannot ride only bumblebees)
  - bumblebee: only ones flying + rideable with (saddle (found inside the overworld dungeon structure))
  - chest: in order to spawn loot with an effect on opened inside the structure (at boss death (with animations: rotation -> grow -> open -> rotation -> shrink -> dead + explosion))
  - honey versions: easy if only retexturing
  LATER
 - vulture: make it hybrid ground also to interact with players and maybe steal their resources when they hit the players

 structures:
 - worldgenerator: reusable Iworldgenerator for generating structures (maybe later when cascading worldgenlag is fixed)
 - (chest)entity dungeon_reward

 biomes:
  - `spring_biome`: with yellowish-colors for bees essentially [in_progress...]

**TODO: move this inside the scope or ideas**
TODO (WorldGen):
  - (advanced) add underground (biomes or caves) with vines and decoration and more minerals
  - (advanced) make a sky island biome
  - (advanced) make a magma/fire biome with fire creatures, vulcano_biome
  - animals ideas: kangaroos (magma cube jump animations to look for), monkey, beaver, pheonix, hog
  - make a prehistorical biome with dinosaurs

**TODO: move this inside the scope or ideas**
TODO (JOBS):
  - Make a separate button that leads to the sharedTasks display with ranking & other things inchaa'Allah
  (POLISH)
  - add scoreboard of current shared task (polish the design of the gui)
  - Make a button the claim global task reward so if players are offline they still get the reward [!!!! REALLY NEEDED !!!!]

  - Make jobs exclusive item rewards for FISHERMAN, TAMER, TRAVELER
  LATER
  - Feat: `/jobs` command needs resetting tasks individually or setting individual tasks completion for admins + testing
  - Add `traveler` job task `structures` discovery later... (using isInsideStructure method inside the generation)
  - allow `/locate` to find custom structures added by mathsmod & chaosmod
  - Add UI toast upon task completion
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward for jobs/market) or cut picsou entity for a better one (idea: racoon that sneaks into bases & steals from players chests)

REFACTOR (when needed):
  - entity package
  - events: needs each one to be inside a specific package, like relative to items inside items, relative to biomes inside biomes...

POLISH:
  - weird offset when the name is too long or unlocalized
  - Shared tasks gui make it in the scoreboard pinnable maybe or just make a visual indicator in the gui
  - additionaloverlayinfos : health+armor icons drawing instead of raw values
  - Add SFX to jobs upon level up (TODO) (useless right now as long as every task completed is a level up
  - bears - detail: have something in their mouth sometimes

IDEAS (Items):
  - add variants later slabs, polished, to honeycomb_block

IDEAS (Bosses):
  - 1: add a biome
  - 2: add a mob (ex: magma biome with magma_bears) that has special loot
  - 3: add a boss that spawns requires the special loot
  - 4: replicate and add variety
  - 5: they loot a good weapon or an armor piece that does something more (or just quest reward (every player gets it))

IDEAS (rework):
  - make the items upgrades instead of crafting others: oxonium -> forge -> allemanite -> forge -> enderite -> forge -> solarite and so on...
  - make the swords block attacks by holding right-click wielding them (pre-1.9 pvp)
  - remake armors (enderite_texture: more like a crying obsidian or maybe just remove it and go crying_obsidian_armor) + give them effects unique (variety)
  - remake allemanite_backpack and make it upgradable with more storage and actually syncs to not loose loot (verify/re-implement)
  - Make a new quest system kinda like `FTB Quests Mod` were you follow a roadmap (more like a separate mod/api)
  - Add a pin task button to make it on the scoreboard (if not too many inside the screen like a todo list)

IDEAS(JOBS):

IDEAS:
  - Add rewards for common tasks for the most efficient player (visual effects too like ranking stats and progress bars vertical animated for satisfaction)
  - Wheat boss like a scarecrow (common task gather a ton of wheat)
  LATER (maybe another ver.)
  - add a spelunkery table which requires translation like (alex's cave mod) (travelers I think to find coords of the structures or buried treasures (traveler job))
  - Custom event only-tipped-arrows allowed
  - InventorySorter: like a little item that when you shift-right-click on, it sorts a container based on names/ids/types... depends on the mode that he's on
  - Also make the items have a rarity/tier so that you can basically reforge everything.
  - Rework the current bosses and entities models (add effects + animations) [PARTIALLY DOING more done like for new things]
  - Skill tree
  - add buried treasure with a minimap
  - Herobrine, Putin, Technoblade, DaquavisMC, Dream bosses
  - Structure loot chest that when opened summons entities that will fight to protect the chest (custom trapped chest)
  - Structures matching the biomes starting with vanilla ones for instance : desert = pyramid, plain = pillagerTower, or wizard tower
  - Cook (like with stats increasing if you have a good alimentation) + if you have a bad one you get `malnutrition effect` and downgrades like you can't deal damage or very low, also you hunger bar shrinks faster
  - Add new lava fish inside the nether with a custom fishing rod too (bober entity fireResistant)
  - Sheepwars
  - Don't forget aerial structures as long as we have the jetpack + elytras(fireworks) (TODO : make it a task like use elytras with tier3 fireworks (traveler)))
  - add variants to vanilla animals like skins and other models, no behavior, just reskin for visual variety
  MINOR
  - add a visual effect to gamemode switch with hotkeys F3+F4
  - add a custom cursor while ingame menus
  - add an entirely customizable gui notes (armor equipped and durability on screen (position, scale, ... everything customizable) like a custom client would do (config disabling feature))
  - gummy bears + candy dimension for christmas
  - add mobs per-biome in vanilla biomes in order to add diversity
  - make the hunt minigame with a map, chests & loot then you have to chase & kill your opponent or a specific player, clutch 1 vs 2 will be entertaining or maybe one player gets an overpowered item and tries to survive against more people that can be very good or maybe it's all players versus one boss or something interesting coop

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
  - Fix: `oxonium_slab` order bottom/top order (on compiled versions (might be caused by lighting or mathsmod too caused by mathsmod:hunter entity))
  - EntityViking clearing inventory on right-click and kills you...
  - DrawerTESR: item icon lights even in the dark + container overflow issues (code issue)
  - Sword Of Wrath orientation towards monster attacked (fix animations)
  - Cascading worldgen lag for generated structures... (seems fixed for now I will let it here as I will add more huge structures later)
  - Fix: bear canSpawnHere needs to check the AABB before (just override method)
  - make my own main menu gui in order to disable it from the mod's config

OPTIMIZATION:
  - Biome gen rework a little bit slower than before, I Might as well remove the biome flower system and come back to IWorldGenerator gen (less overhead)

TODO (Utilities):
  LATER
  - (complex) add a `//copy` based on vanilla `/clone` (maybe because I might recode it in order to be compatible with `//undo` & `//redo` commands) command with completions
  - (registry making it complex) make the `//paste <x> <y> <z> [registry (int)]` take from it (works finally with //undo & //redo)
  - make a ruler item to measure distances between pos1 & pos2
  - make noise values
  EVEN MORE LATE
  - add axis rendering x red, z blue, y green (like structure block does)

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

TODO (last):
  - Make a tool for making easier animations in blockbench directly (plugin or else)
  - Make another tool for DjoCaillou to mod without coding (if not, make tutorials on MCreator for 1.12.2)

PLAYTEST:
  - All jobs task completion + granting rewards correctly (client+server)
  items:
  - player_tracking_compass
  - biome_tracking_compass
  - structure_locator_compass

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
  - chore: `patchouliAPI` removed

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

make gradlew setupDecompWorkspace
then gradlew eclipse
it should be all
