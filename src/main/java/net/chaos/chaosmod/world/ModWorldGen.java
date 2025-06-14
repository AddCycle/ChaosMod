package net.chaos.chaosmod.world;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.FlowerType;
import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import util.Reference;

public class ModWorldGen implements IWorldGenerator {
	private static final Set<ChunkPos> alreadyGenerated = new HashSet<>();
	// private final MapGenMyStructure myStructure = new MapGenMyStructure();
	/*
	 * Overworld = 0
	 * End = 1
	 * Nether = -1
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		/*if (world.provider.getDimension() == 0 && chunkGenerator instanceof ChunkGeneratorOverworld) {
	        tryReplaceVillageGen((ChunkGeneratorOverworld) chunkGenerator);
	    }*/
		switch (world.provider.getDimension()) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16, chunkX, chunkZ);
			break;
		case 1:
			generateEnder(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

    private void tryReplaceVillageGen(ChunkGeneratorOverworld chunkGenerator) {
    	/*try {
            Field villageField = ChunkGeneratorOverworld.class.getDeclaredField("villageGen");

            // Fallback for SRG name in case you're running with MCP-obfuscated environment
            if (villageField == null) {
                villageField = ChunkGeneratorOverworld.class.getDeclaredField("field_73168_g");
            }

            villageField.setAccessible(true);

            Object current = villageField.get(chunkGenerator);
            if (!(current instanceof MapGenCustomVillage)) {
                System.out.println("[ChaosMod] Replacing vanilla village generator with custom one.");
                villageField.set(chunkGenerator, new MapGenCustomVillage());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("[ChaosMod] Failed to replace villageGen:");
            e.printStackTrace();
        }*/
    	try {
            IChunkGenerator gen = chunkGenerator;

            // Check if this is ChunkGeneratorOverworld (vanilla)
            if (gen.getClass().getSimpleName().equals("ChunkGeneratorOverworld")) {
                Field[] fields = gen.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(gen);
                    if (value instanceof MapGenVillage) {
                        System.out.println("[ChaosMod] Replacing village generator: " + field.getName());
                        field.set(gen, new MapGenCustomVillage()); // your custom class
                        return;
                    }
                }
                System.err.println("[ChaosMod] Could not find village generator field.");
            } else {
                System.err.println("[ChaosMod] Not ChunkGeneratorOverworld: " + gen.getClass().getName());
            }
        } catch (Exception e) {
            System.err.println("[ChaosMod] Failed to replace villageGen:");
            e.printStackTrace();
        }
	}

	private void generateEnder(World world, Random random, int x, int z)
    {
    	this.generateOre(ModBlocks.ENDERITE_ORE, Blocks.END_STONE, world, random, 3, 3, x, z, 40, 80);
        generateFlowers(world, random, x, z, 2);
    }

    private void generateOverworld(World world, Random random, int x, int z, int chunkX, int chunkZ)
    {
            if(random.nextInt(4) < 2)
            {
            	this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 6, 5, x, z, 3, 50);
            }
            else
            {
            	this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 5, 5, x, z, 3, 50);
            }
            
            generateFlowers(world, random, x, z, 0);

            if (world.getWorldInfo().isMapFeaturesEnabled()) {
                if (random.nextInt(40) == 0) { // more frequent but I will add this to the config later TODO
                	// trying to center that in the chunk
                    int centerX = chunkX * 16 + 8;
                    int centerZ = chunkZ * 16 + 8;

                    // Start from top and go down to find real surface
                    BlockPos surface = world.getPrecipitationHeight(new BlockPos(centerX, 255, centerZ)).down();
                    IBlockState state = world.getBlockState(surface);

                    // Debug print
                    System.out.println("Block at " + surface + " is " + state.getBlock() + " | Material: " + state.getMaterial());

                    if (state.getMaterial() != Material.WATER) return; // not valid location

                    BlockPos pos = surface; // One block above water

                    if (!world.isAreaLoaded(pos, 16)) return;

                    ChunkPos cp = new ChunkPos(chunkX, chunkZ);
                    if (!alreadyGenerated.add(cp)) return;

                    generateVikingGallion(world, pos, random, new ResourceLocation(Reference.MODID, "viking_gallion"));
                    System.out.println("Generating : " + pos);
                }
            }
    }

    public void generateVikingGallion(World world, BlockPos pos, Random random, ResourceLocation rl) {
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return;
        if (world.isRemote) return;

        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        ResourceLocation gallionLocation = rl;
        Template template = manager.getTemplate(server, gallionLocation);

        if (template == null) return;

        PlacementSettings settings = new PlacementSettings()
            .setIgnoreEntities(false)
            .setMirror(Mirror.NONE)
            .setRotation(Rotation.values()[random.nextInt(Rotation.values().length)])
            .setChunk((ChunkPos) null)
            .setReplacedBlock(null)
            .setIgnoreStructureBlock(true);

        // Disable block updates to prevent recursive generation
        boolean oldCaptureBlockSnapshots = world.captureBlockSnapshots;
        world.captureBlockSnapshots = true; // Disable actual world updates during placement

        template.addBlocksToWorld(world, pos, settings);

        world.captureBlockSnapshots = oldCaptureBlockSnapshots; // Restore default behavior
        BlockPos max = pos.add(template.getSize()); // Get size of structure

        BlockPos.getAllInBox(pos, max).forEach(p -> {
            IBlockState state = world.getBlockState(p);
            world.markAndNotifyBlock(p, world.getChunkFromBlockCoords(p), state, state, 1);
        });
        
        EntityViking viking = new EntityViking(world);
        viking.setPosition(pos.getX() + 21, pos.getY() + 17, pos.getZ() + 20);
        world.spawnEntity(viking);
    }


	private void generateNether(World world, Random random, int x, int z)
    {
		this.generateOre(ModBlocks.ALLEMANITE_ORE, Blocks.NETHERRACK, world, random, 4, 4, x, z, 3, 126);
		generateFlowers(world, random, x, z, 1);
    }

    public void generateOre(Block ore, Block block, World world, Random random, int maxVeinSize, int chancesToSpawn, int x, int z, int minY, int maxY)
    {
        for(int i = 0; i < chancesToSpawn; i++)
        {
            assert maxY > minY;
                assert minY > 0;
                assert maxY < 256 && maxY > 0;

                int posY = random.nextInt(maxY - minY);

                WorldGenerator oreGen = new WorldGenMinable(ore.getDefaultState(), maxVeinSize, BlockMatcher.forBlock(block));
                oreGen.generate(world, random, new BlockPos(x + random.nextInt(16), minY + posY, z + random.nextInt(16)));
        }

    }

    private void generateFlowers(World world, Random rand, int x, int z, int meta) {
    	// FlowerType randomVariant = FlowerType.values()[rand.nextInt(FlowerType.values().length)];

    	// Set block state with the random variant
    	IBlockState flowerState = ModBlocks.CUSTOM_FLOWER.getDefaultState().withProperty(BlockCustomFlower.type, FlowerType.byMetadata(meta));
        BlockPos pos = new BlockPos(x + rand.nextInt(16), 0, z + rand.nextInt(16));
        BlockPos topPos = world.getHeight(pos);

        for (int i = 0; i < 4; i++) { // number of tries for adding like variety as the loot-tables and everything... (thanks to the racist JeanRobertPerez)
            int offsetX = x + rand.nextInt(16);
            int offsetZ = z + rand.nextInt(16);
            int offsetY = topPos.getY();

            BlockPos flowerPos = new BlockPos(offsetX, offsetY, offsetZ);
            /* Biome specific generation */
             Biome biome = world.getBiome(flowerPos);
			 /* if (biome instanceof BiomePlains && world.isAirBlock(flowerPos) && ModBlocks.MY_FLOWER.canPlaceBlockAt(world, flowerPos))
             */
             if (meta == 0) {
            	 ResourceLocation biomeId = Biome.REGISTRY.getNameForObject(biome);
            	 if (biomeId != null && biomeId.toString().equals("chaosmod:giant_mountains")) {
            		 if (world.isAirBlock(flowerPos) && ModBlocks.CUSTOM_FLOWER.canPlaceBlockAt(world, flowerPos)) {
            			 world.setBlockState(flowerPos, flowerState, 2);
            		 }
            	 } else {
            		 return;
            	 }
             }
            	 
             if (world.isAirBlock(flowerPos) && ModBlocks.CUSTOM_FLOWER.canPlaceBlockAt(world, flowerPos)) {
            	 world.setBlockState(flowerPos, flowerState, 2);
             }
        }
    }

}
