# Changelog 1.0.16

# Items

TODO (current version):
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DONE 3 need to add much more tasks and item/money reward system]
  - Add many jobs and items/blocks rewards
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward)

IDEAS:
  - make the fishing get an interface and an interaction with the mouse or keyboard timing to catch something

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

ISSUES:
  - Fix: ModWorldGen viking gallions generated in weird places... #3
  - EntityViking clearing inventory on right-click and kills you...

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
