# Changelog 1.0.16

# Items

TODO (current version):
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement)
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DOING]
  - need to add a marker (job completed) #2

TODO (next version) ?:
  - only generate gallions in `watery` biomes (oceans, copraveg...) #3
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too
  - Fix playlist and soundMgr system (need to reimplement everything)
  - Hunt
  - Market
  - Minimap should render the correct color for the foliage, grass & water of the current biome **DONE**

ISSUES:
  - Edge case jobTasks can give an infinite amount of exp if completed (then going after threshold) #2
  - Fix: ModWorldGen viking gallions generated in weird places... #3
  - weird gray opaque textures while drawing text with chat open (minimap related)

REFACTOR (need):
  - entity package

New:
  - MainMenu custom `LOGO` (overrides `minecraft.png`)
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`
  - added `visited biomes` capability

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
