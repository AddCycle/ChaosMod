# Changelog 1.0.16

# Items

TODO (current version):
  - Add variants to gallions and random loots
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DONE 3 need to add more tasks & some money rewards to later make the market] [DOING]
  - Make all the jobs tasks & rewards from level 1 -> 5 (make content), then tweak or change to make it funnier according to `REWORK` section
  - Make jobs support multiple rewards (Later)
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward for jobs/market)
  - Drop the ChaosMod `advancements` for a new quest system kinda like `FTB Quests Mod` (more like a separate mod/api)

IDEAS:
  - InventorySorter: like a little item that when you shift-right-click on, it sorts a container based on names/ids/types... depends on the mode that he's on
  - Sword that kinds of follows you around if you cast it and hits mobs (should be an endgame item tho...)
  - Also make the items have a rarity/tier so that you can basically reforge everything.
  - Rework the current bosses and entities models (add effects + animations)
  - Skill tree
  - Add `traveler` job with `discovered biomes` along with `structures` later...
  - add buried treasure
  - Custom `super_totem` that triggers even when isn't held in hands
  - Herobrine boss
  - Poutine boss
MINOR:
  - add a visual effect to gamemode switch with hotkeys F3+F4

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

TODO (next version) ?:
  - only generate gallions in `watery` biomes (oceans, copraveg...) #3
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too
  - Hunt
  - Market
  - Fix playlist and soundMgr system (need to reimplement everything)
  - add `magnet` to structures loots

OPTIMIZATION:
  - Biome gen rework a little bit slower than before, I Might as well remove the biome flower system and come back to IWorldGenerator gen (less overhead)

ISSUES:
  - Fix: bosses loots can burn in the lava
  - Fix: `/jobs` command not working properly (needs code fix)
  - Fun: biomes are kinda redundant, fix: add new custom decoration blocks, structures, mobs, tamable animals, each one being exclusive to the biome to match the color/style of each 4 biomes
  - Fix: When `mathsmod` not loaded, job reward for `tamer` is null
  - Fix: `oxonium_slab` order bottom/top order (on compiled versions)
  - Fix: ModWorldGen viking gallions generated in weird places... #3
  - EntityViking clearing inventory on right-click and kills you...
  - DrawerTESR: item icon lights even in the dark

REFACTOR (need):
  - entity package

New:
  - MainMenu custom `LOGO` (overrides `minecraft.png`)
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`
  - added `visited biomes` capability && `/biomes` command
  - Fishing Mechanic where timing is the key to catch something (Undertale inspired thx `Toby Fox`)
  - ChaosMasterBoss in the `custom_dim` deals `blue_fire_damage` with a new overlay effect
  - feat: `jobs` grants you `block/item` reward leveling up
  - feat: added `magnet` item that pickups nearby loot
  - feat: added a quick way to change gamemode [hotkey F3+F4]

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

MODEL-ISSUES:
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:atm_block#inventory for item "chaosmod:atm_block", normal location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:item/atm_block with loader VanillaLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:302) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: java.io.FileNotFoundException: chaosmod:models/item/atm_block.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:atm_block#inventory for item "chaosmod:atm_block", blockstate location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:atm_block#inventory with loader VariantLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:296) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading blockstate for the variant chaosmod:atm_block#inventory: 
java.lang.Exception: Could not load model definition for variant chaosmod:atm_block
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:269) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadBlock(ModelBakery.java:121) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadBlocks(ModelLoader.java:223) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:150) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
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
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:portal_frame#inventory for item "chaosmod:portal_frame", normal location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:item/portal_frame with loader VanillaLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:302) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: java.io.FileNotFoundException: chaosmod:models/item/portal_frame.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:portal_frame#inventory for item "chaosmod:portal_frame", blockstate location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:portal_frame#inventory with loader VariantLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:296) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:chaosland_portal#normal for blockstate "chaosmod:chaosland_portal"
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:chaosland_portal#normal with loader VariantLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.registerVariant(ModelLoader.java:235) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadBlock(ModelBakery.java:153) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadBlocks(ModelLoader.java:223) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:150) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 21 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading blockstate for the variant chaosmod:chaosland_portal#normal: 
java.lang.Exception: Could not load model definition for variant chaosmod:chaosland_portal
	at net.minecraftforge.client.model.ModelLoader.getModelBlockDefinition(ModelLoader.java:269) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadBlock(ModelBakery.java:121) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadBlocks(ModelLoader.java:223) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:150) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
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
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:shapeshifter_block#inventory for item "chaosmod:shape_shifter_block", normal location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:item/shapeshifter_block with loader VanillaLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:302) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: java.io.FileNotFoundException: chaosmod:models/item/shapeshifter_block.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant chaosmod:shapeshifter_block#inventory for item "chaosmod:shape_shifter_block", blockstate location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model chaosmod:shapeshifter_block#inventory with loader VariantLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:296) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant minecraft:air#inventory for item "minecraft:air", normal location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model minecraft:item/air with loader VanillaLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:302) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: java.io.FileNotFoundException: minecraft:models/item/air.json
	at net.minecraft.client.resources.FallbackResourceManager.getResource(FallbackResourceManager.java:69) ~[FallbackResourceManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.getResource(SimpleReloadableResourceManager.java:65) ~[SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadModel(ModelBakery.java:334) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.access$1400(ModelLoader.java:115) ~[ModelLoader.class:?]
	at net.minecraftforge.client.model.ModelLoader$VanillaLoader.loadModel(ModelLoader.java:861) ~[ModelLoader$VanillaLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/ERROR] [FML]: Exception loading model for variant minecraft:air#inventory for item "minecraft:air", blockstate location exception: 
net.minecraftforge.client.model.ModelLoaderRegistry$LoaderException: Exception loading model minecraft:air#inventory with loader VariantLoader.INSTANCE, skipping
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:161) ~[ModelLoaderRegistry.class:?]
	at net.minecraftforge.client.model.ModelLoader.loadItemModels(ModelLoader.java:296) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelBakery.loadVariantItemModels(ModelBakery.java:175) ~[ModelBakery.class:?]
	at net.minecraftforge.client.model.ModelLoader.setupModelRegistry(ModelLoader.java:151) ~[ModelLoader.class:?]
	at net.minecraft.client.renderer.block.model.ModelManager.onResourceManagerReload(ModelManager.java:28) [ModelManager.class:?]
	at net.minecraft.client.resources.SimpleReloadableResourceManager.registerReloadListener(SimpleReloadableResourceManager.java:121) [SimpleReloadableResourceManager.class:?]
	at net.minecraft.client.Minecraft.init(Minecraft.java:560) [Minecraft.class:?]
	at net.minecraft.client.Minecraft.run(Minecraft.java:422) [Minecraft.class:?]
	at net.minecraft.client.main.Main.main(Main.java:118) [Main.class:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:135) [launchwrapper-1.12.jar:?]
	at net.minecraft.launchwrapper.Launch.main(Launch.java:28) [launchwrapper-1.12.jar:?]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_202]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_202]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_202]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_202]
	at net.minecraftforge.gradle.GradleStartCommon.launch(GradleStartCommon.java:97) [start/:?]
	at GradleStart.main(GradleStart.java:25) [start/:?]
Caused by: net.minecraft.client.renderer.block.model.ModelBlockDefinition$MissingVariantException
	at net.minecraft.client.renderer.block.model.ModelBlockDefinition.getVariant(ModelBlockDefinition.java:83) ~[ModelBlockDefinition.class:?]
	at net.minecraftforge.client.model.ModelLoader$VariantLoader.loadModel(ModelLoader.java:1175) ~[ModelLoader$VariantLoader.class:?]
	at net.minecraftforge.client.model.ModelLoaderRegistry.getModel(ModelLoaderRegistry.java:157) ~[ModelLoaderRegistry.class:?]
	... 20 more
[18:49:56] [Client thread/FATAL] [FML]: Suppressed additional 3 model loading errors for domain chaosmod
