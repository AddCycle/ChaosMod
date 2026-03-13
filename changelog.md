# Changelog 1.0.16

# Items

TODO:
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return)
  - Store the visited biomes into a new capability
  - Move credits after beating `ChaosMaster Boss`
  - Send a `cutscene` Packet to every player
  - Fix playlist and soundMgr system (need to reimplement everything)
  - Jobs/Quest system
  - Market
  - Hunt
  - Dungeon & custom structures with a bit of stuff for exploration in the overworld too

REFACTOR (need):
  - entity package

New:
  - MainMenu custom `LOGO`
  - AllemaniteHoe: farms 3x3
  - OxoniumAxe: `sawmill mode` harvests the entire trunk
  - BiomeFinderCommand: `/biome [biome_id] [range]`

Changes:
  - OxoniumPickaxe: `vein miner` mode compatible with other mods `ores`
  - CustomBricks: `oxonium`, `allemanite`, `enderite` craft changed from `stonebricks` to `brick_block`
  - Pixou drops the stole `mineral` blocks (I think only gold works right now)
  - Removed garbage blocks as andesite, diorite, granite from generation
  - Optimized `/find <block_id> [range]` command
  - refactor: automatic packet registration with custom annotation @ModPackets(modid, side)
