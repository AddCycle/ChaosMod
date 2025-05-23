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
            &emsp;Brave boots, \
            &emsp;Brave helmet, \
            &emsp;Oxonium Hallebard, \
            &emsp;Oxonium Necklace, \
            &emsp;Oxonium Bow, \
        ]; \
        blocks -> [ \
            &emsp;Oxonium Bricks, \
            &emsp;Oxonium Block \
        ];

- <b>Allemanite (2) :</b>
    - Location : Nether, biome="Nether Cave", loot-chests="dungeon structures"
    - Rarity : Rare, max-vein-size=2
    - Purposes : \
        items -> [ \
            Allemanite Extinguisher, \
            Allemanite Backpack, \
            Allemanite Pickaxe, \
            Allemanite Sword \
        ]; \
        blocks -> [ \
            Allemanite Bricks, \
            Allemanite Block \
        ];

- <b>Enderite (3):</b>
    Location : End, biome="Chaos Biome", inside of the Meteorites
    Rarity : Legendary (1 all the 10000 blocks radius)
    Purposes : \
        items -> [ \
            \tEnderite Tinkerer's Hammer \
            \tEnderite Thunder \
        ]; \
        blocks -> [ \
            \tEnderite Bricks, \
            \tEnderite Block \
            \tEnderite Forge Interface Block \
        ]; \
        machines -> [ \
            Enderite Forge \
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

- <b>Allemanite Bricks:</b>
    - Description: "Decorative blocks with shining pink particles"
    - Usage: "You can make a house with it..."
    - Craft : [
            "bbb",
            "bab",
            "bbb",
        { b: "minecraft:bricks", a: "chaosmod:allemanite\_ingot" }
    ];
    - Properties: "Resists to explosions"

- <b>Allemanite Block:</b>
    - Description: "So beautiful that it may be stolen"
    - Usage: "Pixou the duck can steal them if you are not careful enough"
    - Craft : [
            "aaa",
            "aaa",
            "aaa",
        { a: "chaosmod:allemanite\_ingot" }
    ];
    - Properties: "Can be stolen by Pixou"

- <b>Enderite Forge Interface Block</b>
    - Description: "Used to forge Ancient Weapons...",
    - Usage : "No real utility by itself"
    - Craft : [
        "eee",
        "ewe",
        "eee",
        { "w": "minecraft:glass", "e": "chaosmod:enderite\_ingot" }
];

- <b>Enderite Bricks:</b>
    - Description: "Decorative blocks with shining violet particles"
    - Usage: "You can make a house with it..."
    - Craft : [
            "bbb",
            "beb",
            "bbb",
            "count": "32",
        { b: "minecraft:bricks", e: "chaosmod:enderite\_ingot" }
    ];
    - Properties: "Resists to explosions"

- <b>Enderite Block:</b>
    - Description: "So beautiful that it may be stolen"
    - Usage: "Pixou the duck can steal them if you are not careful enough"
    - Craft : [
            "eee",
            "eee",
            "eee",
        { e: "chaosmod:enderite\_ingot" }
    ];
    - Properties: "Can be stolen by Pixou"

- <b>Rainbow grass :</b>
    - Description: "Has a random light colored tint when put"
    - Usage: "Random color (not dark) + resists to explosions (decorates the Ender Garden biome)"
    - Craft : [
            "bbb",
            "bdb",
            "bbb",
        { d: "minecraft:dirt", b: "minecraft:bone\_meal" }
    ];
    - Properties: "Resists to explosions"

- <b>Colored logs :</b>
    - Description: "3 colors : red, blue, dark\_violet"
    - Usage: "decorates the Ender garden biome"
    - Craft : [
            "bbb",
            "blb",
            "bbb",
        { l: "minecraft:leaves", b: "minecraft:bone\_meal" }
    ];
    - Properties: "Resists to explosions"

- <b>Colored leaves :</b>
    - Description: "3 colors : red, blue, dark\_violet"
    - Usage: "decorates the Ender garden biome"
    - Craft : [
            "bbb",
            "blb",
            "bbb",
        { l: "minecraft:log", b: "minecraft:bone\_meal" }
    ];
    - Properties: "Resists to explosions"

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

- <b>Oxonium Security helmet :</b>
    - Description: "The sky is falling on us heads... (asterix)"
    - Usage : "Takes -10% damage from falling rocks"
    - Craft : [
            "",
            "xxx",
            "x x",
        { x: "chaosmod:oxonium\_ingot"}
    ];
    - Stats : [
        "durability": "150",
        "special": "-10% damage rocks"
    ];

- <b>Brave Bow :</b>
    - Description: "You are so brave that you needed this..."
    - Usage : "Shoots the boss (like a brave)"
    - Craft : [
            "fx ",
            "f x",
            "fx ",
        { f: "minecraft:feather", x: "chaosmod:oxonium\_ingot"}
    ];
    - Stats : [
        "durability": "400" (a bit more than the original bow),
        "armor": "-25%",
        "condition": "health < 40%" >> {
            "special": "deals x2 damage",
        }
    ];

- <b>Oxonium Hallebard:</b>
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

- <b>Allemanite Extinguisher:</b>
    - Description: "OMG, this thing is my safety belt...",
    - Usage : "When right-click the ground, it cool you down ?"
    - Craft : [
            "aai",
            "aa ",
            "aa ",
        { a: "chaosmod:allemanite\_ingot", i: "minecraft:iron\_ingot" }
    ];
    - Stats : [
        "durability": "10",
        "cooldown": "0",
    ];

- <b>Allemanite Backpack</b>
    - Description: "Oh so you think your inventory isn't enough ?",
    - Usage : "When right-clicked, opens a bound inventory"
    - Craft : [
            "aal",
            "aal",
            "aal",
        { a: "chaosmod:allemanite\_ingot", l: "minecraft:leather" }
    ];
    - Stats : [
        "durability": "none",
        "slots": "chest number"
    ];

- <b>Allemanite Pickaxe</b>
    - Description: "Dig through hell...",
    - Usage : "Can dig 3x3 blocks when right-clicking blocks"
    - Craft : [
            "aaa",
            " s ",
            " s ",
        { a: "chaosmod:allemanite\_ingot", s: "minecraft:stick" }
    ];
    - Stats : [
        "durability": "1000",
        "special": ""
    ];

- <b>Allemanite Sword</b>
    - Description: "Kills through hell...",
    - Usage : "Burns the ennemies"
    - Craft : [
            " a ",
            " a ",
            " s ",
        { a: "chaosmod:allemanite\_ingot", s: "minecraft:stick" }
    ];
    - Stats : [
        "durability": "4000",
        "special": "burns ennemies with red flames (visual effect only)"
    ];

- <b>Enderite Tinkerer's Hammer:</b>
    - Description: "Are you an engineer now ?"
    - Usage: "Used to build machinery : Right-click when the pattern is build to finish the assembly"
    - Craft : [
            "eee",
            "ese",
            " s ",
        { s: "minecraft:stick", e: "chaosmod:enderite\_ingot" }
    ];
    - Stats : [
        "effects": [ "heavy": "x2" ]
    ];

- <b>Enderite Thunder:</b>
    - Description: "Throws a powerful thunder at your ennemis dealing damages"
    - Usage: "Throwable thunder that flames your enemy with blue fire effect"
    - Craft : [
            "eee",
            "ese",
            " s ",
        { s: "minecraft:stick", e: "chaosmod:enderite\_ingot" }
    ];
    - Stats : [
        "effects": [ "heavy": "x2" ]
    ];

- <b>All-in-one Bow :</b>
    - Description: "I waited so long to have it"
    - Usage : "Grants speed + burns enemies with blue flames"
    - Craft : [
            "fx ",
            "f e",
            "fa ",
        { f: "minecraft:feather", x: "chaosmod:oxonium\_ingot", a: "allemanite\_ingot", e: "enderite\_ingot"}
    ];
    - Stats : [
        "durability": "unbreakable"
        "condition": "after 10 arrows, a special one that deals x3 damage with a 50% chance" >> {
            "special": "deals x3 damage",
        }
    ];

- <b>All-in-one Sword :</b>
    - Description: "I waited so long to have it"
    - Usage : "Grants jump boost (II) + burns enemies with blue flames + knockback (II)"
    - Craft : [
            " xe",
            " ea",
            "s  ",
        { s: "minecraft:stick", x: "chaosmod:oxonium\_ingot", a: "allemanite\_ingot", e: "enderite\_ingot"}
    ];
    - Stats : [
        "durability": "unbreakable"
        "condition": "after 10 hits, a special one that deals no damage 100% but with a knockback 5 with a 50% chance" >> {
            "special": "kb 5",
        }
    ];

- <b>Giant heart</b>
    - Description : "Doug doug"
    - Usage : "In order to spawn the Nether blaze boss",
    - Dropped by : "Mountain Giant"

- <b>Blaze Heart</b>
    - Description : "Doug doug"
    - Usage : "To spawn the Eye of Truth boss",
    - Dropped by : "Nether boss"

- <b>Chaos Heart</b>
    - Description : "Doug doug"
    - Usage : "To spawn the Chaos Master",
    - Dropped by : "Eye of Truth"

<h2>New bosses [4] :</h2>

- <b>Mountain Giant (overworld):</b>
    - Description: "What a piece of cake !"
    - Usage: "It's easier to beat him with the Oxonium Hallebard and the Brave boots"
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

<h2>New GUIs [5] :</h2>

- <b>Chaos Sage interface :</b>
    - Usage: "An interface in order to display the recipies/lore/bosses/tips..."
    - Properties : {
        "height": "100%",
        "weight": "100%",
        "background": "dark-transparent"
    }

- <b>Vikings quest interface :</b>
    - Usage: "An interface in to describe the quest & dispose the items"
    - Properties : {
        "height": "100%",
        "weight": "100%",
        "background": "dark-transparent"
    }

- <b>Enderite Forge :</b>
    - Description: "A basic crafting table I think"
    - Usage: "A classic interface with 1 output to then craft All-in-one items"
    - Properties : {}

<h2>New entities [6] :</h2>

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

- <b>Vikings</b>
    - Description: "So basically, I was terrifying people"
    - Usage: "This pnj will give you a quest, then when you complete it, your stats with certain items will change"
    - Properties: {
        "location": "Structure -> Raid ships",
        "passive": true,
        "health": "30",
        "attack": "4",
        "sound": "azeroth\_sound",
        "animations": [ "attack", "death", "on\_kill\_player" ],
    }

- <b>Little Big giants</b>
    - Description: "Next time you see me, I will be tall as the tree..."
    - Usage: "This pnj will give you a quest, then when you complete it, your stats with certain items will change"
    - Properties: {
        "location": "Structure -> Giant Mountain caves hut",
        "passive": true,
        "health": "20",
        "attack": "1",
        "sound": "caveman\_sound",
        "animations": [ "cry", "death", "attack" ]
    }

<h2>New Tileentities [7] :</h2>

- <b>Boss Altar</b>
    - Description: "An ancient artifact to summon bosses"
    - Usage : "Right-Click without item + overworld -> Mountain Giant | heart + dimension = hard"
    - Craft-pattern : "None"

<!-- FIXME -->
- <b>Enderite Forge</b>
    - Description: "Used to forge Ancient Weapons...",
    - Usage : "Ancient weapons are to be added in order to fight the Chaos Master"
    - Craft-pattern : "3x3 structure with allemanite blocks, 1 enderite inside and with a forge interface block in front"
<!-- END OF FIXME -->

- <b>Brave Trophy</b>
    - Description : "You don't look that strong"
    - Usage : "Just a decorative trophy from the boss, grants a special effect +10% health",
    - Dropped by : "Mountain Giant

- <b>Firefighter Trophy</b>
    - Description : "What a pity accomplishment"
    - Usage : "Just a decorative trophy from the boss, grants fire resistance",
    - Dropped by : "Nether boss"

- <b>Eye eye eye Trophy</b>
    - Description : "I'm looking at you..."
    - Usage : "Just a decorative trophy from the boss, grants a special effect +10% strengh",
    - Dropped by : "Eye of Truth"

- <b>Talent Trophy</b>
    - Description : "So you though this was the outcome..."
    - Usage : "Just a decorative trophy from the boss, grants a special effect you are immune to debuffs",
    - Dropped by : "Chaos Master (Thunder)"

- <b>Ender flowers :</b>
    - Description : "3 decorative flowers : GOLD, GREEN, DARK\_VIOLET"
    - Usage : "In order to decorate the Ender Garden biome",

<h2>New biomes [8] :</h2>

- <b>Higher Mountains:</b>
    - Description: "Just a biome with cliffs and some water in bottom of them and snow on top of them with rocks and maoris totems"
    - Usage: "Find a flat structure containing an altar in order to spawn the Mountain Giant"
    - Content: [ "Winter trees", "caves", "boss fight area" ]
    - Events: ["draw a message on the screen like -> Welcome to the Giant Mountains"]

- <b>Nether Caves</b>
    - Description: "No air surrounding it just in the netherrack, some caves dig into, containing mobs and some loots"
    - Usage: "Inside the structures, you can find an altar to summon the Nether Revenger Blaze"
    - Content: [ "light", "some decoration blocks", "boss fight area" ]
    - Events: [ "draw a message on the screen like -> Welcome inside the Nether Caves" ]

- <b>Ender Garden</b>
    - Description: "Ender biome garden with flowers, grass, lights"
    - Usage: "Just a decoration biome"
    - Content: [ "light", "some decoration blocks", "boss fight area" ]
    - Events: [ "draw a message on the screen like -> Welcome to the Chaos Fight" ]

- <b>Chaos Biome</b>
    - Description: "So when you kill the Ender Dragon, you and you team will be teleported to a platform watching the boss area platform will be rebuilt to a new Chaos Biome"
    - Usage: "You can only find the eye looking towards you in the center summoned by the altar"
    - Content: [ "dark", "some decoration blocks", "boss fight area" ]
    - Events: [ "draw a message on the screen like -> Welcome to the Chaos Fight" ]

<h2>New Structures [9] :</h2>

- <b>Cup of the World - Mountain Giant spawning area : </b>
    - Properties : "A large flat area like a frozen colloseum that is quite big but not too much because we want a fight preventing the escape too far from the boss"
    - Special : "An altar in the center to spawn the Giant boss"

- <b>Nether Boss chamber : </b>
    - Properties : "Just a chamber quite large to grants dodging capacity to the player inside the Nether caves that prevents the boss and the player from escaping"
    - Special : "An altar in the center to spawn the Giant boss"

- <b>End fortresses :</b>
    - Properties : "Just a chamber fortress like the Nether one but with monster waves mechanics, when the player enters it, the chamber locks its door and then all the players have to die to unlock itself again for another attempt"

<h2>New dimension [10] :</h2>

- <b>Chaos Land:</b>
    - Description: "It is basically just a flat world with the Chaos boss waiting for you while summoned"
    - Properties: "Some dark trees, dark environnement, only some small lights"
    - Events: [ "when killing the boss -> Congratulations ! You have killed the boss !" then "teleports you to the overworld" ]

<h2>Sound Effects [11] :</h2>

- When I finish everything, I will work on the sound effects & other stuff improvement
    - Because it needs a version on release and another unofficial version for us to play with friends and copyrighted musics and sounds :(
    - Ideas (ambient sounds) : [ "jjk gojo purple hollow", "minecraft popular music", "courtesy call", "fire force op 1", "fairy tail fiesta", "the promized neverland op", "naruto shippuden op1", "hxh", "tokyo revengers", "parazyte", "death note op1", "mha op1", "seven deadly sins op1", "jojo op2", "black clover op3", "demon slayer op2", "sao op1", "jjk op3", "centuries (for the Mountain Giant boss)", "jjk specialz op", "chainsawman op 1 (banger innatendu !!!)" ]

**Amazing idea, so if JeanRobertPerez is upgrading his mod in newer versions, I will add when you draw a weapon, it plays the sound from Ted 2 FILM scene when TED fires with the gun ! \
Very funny scene if you want my opinion !!**
