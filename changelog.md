# Changelog 1.0.16

# Items

TODO (current version):
  - add a horizontal scollbar to the jobsGui
  - Add variants to gallions and random loots
  - Jobs & Task (Quest) systems (1: refactor, 2: planning, 3: implement, 4: test, 5: polish) [DONE 3 need to add more tasks & some money rewards to later make the market]
  - Make all the jobs tasks & rewards from level 1 -> 5 (make content), then tweak or change to make it fun according to `REWORK` section
  - Make jobs support multiple rewards (Later)
  - Add a utility for `Pixou` stealing precious blocks (giving you something in return) (maybe a quest/job achievement hidden reward for jobs/market)
  - Drop the ChaosMod `advancements` for a new quest system kinda like `FTB Quests Mod` (more like a separate mod/api)

IDEAS:
  - InventorySorter: like a little item that when you shift-right-click on, it sorts a container based on names/ids/types... depends on the mode that he's on
  - Sword that kinds of follows you around if you cast it and hits mobs (should be an endgame item tho...)
  - Also make the items have a rarity/tier so that you can basically reforge everything.
  - Rework the current bosses and entities models (add effects)
  - Skill tree
  - Add `traveler` job with `discovered biomes` along with `structures` later...
  - Custom totem that triggers even when isn't held in hands
  - Add `Magnet item` (right-click to activate/deactivate) then make a slot to equip and run (passive ability)
  - Herobrine boss
  - Poutine boss

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
  - feat: added `magnet` item that pickups nearby loot (CREATIVEONLY until exploration features)

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
