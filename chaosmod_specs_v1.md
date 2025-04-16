<h1 align="center">ChaosMod 1.12.2 - project specs</h1>

<hr>

## Things to implement :

Goal : Having fun through a clear lore and don't feel redundant

## The lore :
    Four overpowerered civilizations were fighting leading to gigantic battles annihilating themselves. But for what purpose were they fighting ? The Chaos Master told them to fight for the Cube of Chaos because if another tribe finds it, they will destroy the world !
    So in order to preserve peace, they had to find it first leading to massive sacrifices thinking every tribe was doing the right thing.
    All of that blood to stop the destruction of the world...
    There was something not right, and the heroes of every civilization knew it, they travelled the world instead of fighting and found 3 altars through dimensions.
    So they used these three altars to seal the Chaos Master inside the cube of chaos but seal will not least more than 1000 years. So you have to anticipate and kill the Chaos Master to stop the tyrant from destroying the world & make him pay for his crimes !
    The Goal = "Kill the guardians to awaken the Boss of the Chaos and kill him to save the honor of the past civilians"
    Conclusion = "To much ambition of power leads to annihilation"

<h2>New minerals [1] :</h2>

- <b>Oxonium (1):</b>
    - Location : Overworld, biome="Giant Mountains", inside of the mountains
    - Rarity : Uncommon (a little more than iron), max-vein-size=4
    - Purposes : \
        items -> [ \
            Brave boots, \
            Oxonium Hallebarde, \
            Oxonium Necklace, \
        ]; \
        blocks -> [ \
            Oxonium Bricks, \
            Oxonium Block \
        ]; \

- <b>Allemanite (2) :</b>
    - Location : Nether, biome="Nether Cave", loot-chests="dungeon structures"
    - Rarity : Rare, max-vein-size=2
    - Purposes : \
        items -> [ \
            Allemanite Extinguisher, \
            Allemanite Backpack, \
            Allemanite Pickaxe, \
        ]; \
        blocks -> [ \
            Allemanite Bricks, \
            Allemanite Block \
        ]; \

- <b>Enderite (3):</b>
    Location : End, biome="Chaos Biome", inside of the Meteorites
    Rarity : Legendary (1 all the 10000 blocks radius)
    Purposes :
        items -> [
            Enderite Tinkerer's Hammer
            Enderite Thunder
        ];
        blocks -> [
            Enderite Bricks,
            Enderite Block
        ];
        machines -> [
            Enderite Forge
        ];

<h2>New items [3] :</h2>

- <b>Brave boots :</b>
    - Description: "You are so brave that you need these boots in case of..."
    - Usage : "Escape the combats (like a brave)"
    - Craft : [
            "",
            "f x",
            "x x",
        { f: "minecraft:feather", x: "chaosmod:oxonium\_ingot"}
    ];
    - Stats : [
        "durability": "500",
        "armor": "-10%",
        "condition": "health < 20%" >> {
            "speed": "2",
            "jump\_boost": 3,
            "special": "not bothered by obstacles",
            "feather\_falling": "no damage" 
        }
    ];

- <b>Oxonium Hallebarde:</b>
    - Description: "Yolo, you have nothing more to lose !",
    - Usage : "When right-click the ground, it does an AOE attack that stunts the ennemis in range"
    - Craft : [
            "xxx",
            "xix",
            " i ",
        { i: "minecraft:stick", x: "chaosmod:oxonium\_ingot" }
    ];
    - Stats : [
        "durability": "1200",
        "speed": -20%,
        "swing\_speed": -20%,
        "damage": +10%/viking\_helped,
        "special-aoe": "knockback 1" -> "cooldown": "3"
    ];

- <b>Oxonium Necklace :</b>
    - Description: "Grants a little bit of life if you are in a trouble as a brave fighter"
    - Usage: "Can be equipped through the neck slot, grants regeneration 1"
    - Craft : [
            "sss",
            "s s",
            " x ",
        { s: "minecraft:string", x: "chaosmod:oxonium\_ingot" }
    ];
    - Stats : [
        "effects": [ "regeneration": "1" ]
    ];

<h2>New blocks [2] :</h2>

- <b>Oxonium Bricks:</b>
    - Description: "Decorative blocks with shining blue particles"
    - Usage: "You can make a house with it..."
    - Craft : [
            "bbb",
            "bxb",
            "bbb",
        { b: "minecraft:bricks", x: "chaosmod:oxonium\_ingot" }
    ];
    - Properties: "Resists to explosions"

- <b>Oxonium Block:</b>
    - Description: "So beautiful that it may be stolen"
    - Usage: "Pixou the duck can steal them if you are not careful enough"
    - Craft : [
            "xxx",
            "xxx",
            "xxx",
        { x: "chaosmod:oxonium\_ingot" }
    ];
    - Properties: "Can be stolen by Pixou"

<h2>New entities :</h2>

- <b>Pixou:</b>
    - Description: "A little duck that steals your precious ressources"
    - Usage: "If you caught it, he will drop you something"
    - Properties: {
        "passive": true,
        "health": "10",
        "attack": "0",
        "sound": soundboard\_duck,
        "animations": [ "sneaking", "running", "breaking\_block", "dropping" ],
        "caught": "drop 30 emeralds"
    }

- <b>Sage of Chaos (1 per dimension):</b>
    - Description: "Knows a lot"
    - Usage: "He will guide the player through his mission, with crafts, bosses pattern -> when right click-ed open a GUI which is a guide"
    - Properties: {
        "location": "nearby the portals of players/spawn",
        "passive": true,
        "health": "unkillable",
        "attack": "0",
        "sound": "pere\_noel",
        "animations": [ "idle sitting in the air (floating)" ]
    }

<h2>New bosses:</h2>

- <b>Mountain Giant (overworld):</b>
    - Description: "What a piece of cake !"
    - Usage: "It's easier to beat him with the Oxonium Hallebarde and the Brave boots"
    - Pattern: {
        "Phase1": "Throws rocks at players",
        "Phase2": "Throws menhirs at players which does x2 damage",
        "Phase3": "Adds to that smashing the floor which send the in range players flying"
    }
    - Stats: {
        "health": "100",
        "damage": { "rocks": "4", "menhirs": "8", "smash": "9" },
        "cooldown": { "rocks": "3", "menhirs": "4", "smash": "10" }
        "loots": { "brave\_trophy": "1", "giant\_heart": "1" }
    }

- <b>Revenge Blaze (nether):</b>
    - Description: "Watch out, you don't want to burn yourself !"
    - Usage: "It's easier to beat him with Allemanite items (extinguisher)"
    - Pattern: {
        "Phase1": "Throws flames at players like a normal blaze",
        "Phase2": "Heats up to twice the temperature and throws blue flames (x3 burning time) + immune to arrows"
    }
    - Stats: {
        "health": "70",
        "damage": { "flames": "2", "blues": "4" },
        "cooldown": { "all\_flames": "2" },
        "loots": { "firefighter\_trophy": "1", "blaze\_heart": "1" }
    }

- <b>Eye of Truth (End):</b>
    - Description: "He's watching you..."
    - Usage: "It's easier to beat him with Enderite combat items & armor"
    - Pattern: {
        "Phase1": "Spawns ennemies like zombies with stuff",
        "Phase2": "Spawns ennemies like skeletons with stuff",
        "Phase3": "Spawns ennemies like endermans with stuff",
        "Phase4": "Throws laser until you defeat him by shooting his eye"
    }
    - Stats: {
        "health": "90",
        "damage": { "laser": "3" },
        "cooldown": { "laser": "5" },
        "loots": { "eye\_eye\_eye\_trophy": "1", "chaos\_heart": "1" }
    }

- <b>Chaos Master (Thunder):</b>
    - Description: "So you think you can beat me huh !"
    - Usage: "Behaves like a thunder dragon"
    - Pattern: {
        "Phase1": "Basically he throws you fireballs",
        "Phase2": "Now he throws you fireballs & fire",
        "Phase3": "Throws thunder",
        "Phase4": "Throws fire & thunder",
        "Phase5": "Throws blue fire & thunder",
        "Phase6": "Transforms into a dark-herobrine boss & comes for you by teleport and backstabbing"
    }
    - Stats: {
        "health": "200",
        "damage": { "all": "4" },
        "cooldown": { "all": "5" },
        "loots": { "talent\_trophy": "1", "solarite\_key": "1" }
    }
