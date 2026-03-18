# Changelog 1.0.16

# Items

TODO (current version):
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DONE 3 need to add much more tasks and item/money reward system]
  - Add many jobs and items/blocks rewards
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward)

TODO (next version) ?:
  - only generate gallions in `watery` biomes (oceans, copraveg...) #3
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too
  - Hunt
  - Market
  - Fix playlist and soundMgr system (need to reimplement everything)

ISSUES:
  - weird gray opaque textures while drawing text with chat open (mostly chat & minimap related)
  - Fix: ModWorldGen viking gallions generated in weird places... #3

REFACTOR (need):
  - entity package

New:
  - MainMenu custom `LOGO` (overrides `minecraft.png`)
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`
  - added `visited biomes` capability && `/biomes` command

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
