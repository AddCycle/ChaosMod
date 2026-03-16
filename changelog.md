# Changelog 1.0.16

# Items

TODO (next version):
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return)
  - Store the visited biomes into a new capability
  - Fix playlist and soundMgr system (need to reimplement everything)
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DOING]
  - Market
  - Hunt
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too
  - need to add a marker (completed) #2
  - only generate gallions in `watery` biomes (oceans, copraveg...) #3

ISSUES:
  - Bosses like `mountain_giant` can despawn if the spawn is too far from the death place
  - Edge case jobTasks can give an infinite amount of exp if completed (then going after threshold) #2
  - Fix: ModWorldGen viking gallions generated in weird places... #3
  - ChaosMaster dropping too many items on SP (maybe too on MP)
  - Credits aren't displaying

REFACTOR (need):
  - entity package

New:
  - MainMenu custom `LOGO`
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`

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
