package net.chaos.chaosmod.world;
import static net.chaos.chaosmod.config.ModConfig.SERVER;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.chaos.chaosmod.blocks.BlockRose;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Biomes;
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
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import util.Reference;

// FIXME : flowers cascading worldgen + viking gallion in a separate worldgen ?
public class ModWorldGen implements IWorldGenerator {
	private static final Set<ChunkPos> alreadyGenerated = new HashSet<>();
	private static final List<Biome> allowedBiomes = Arrays.asList(Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN);

	/*
	 * Overworld = 0
	 * End = 1
	 * Nether = -1
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
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

    private void generateEnder(World world, Random random, int x, int z)
    {
    	this.generateOre(ModBlocks.ENDERITE_ORE, Blocks.END_STONE, world, random, 3, 3, x, z, 40, 80);
//        generateFlowers(world, random, x, z, ModBiomes.SPRING_BIOME, ((BlockRose)ModBlocks.ROSE_FLOWER).getStateFromMeta(0));
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

        generateFlowers(world, random, x, z, ModBiomes.SPRING_BIOME, ((BlockRose)ModBlocks.ROSE_FLOWER).getStateFromMeta(0));

    	if (!world.getWorldInfo().isMapFeaturesEnabled()) {
    		return;
    	}
    	
//    	processVikingGallionSpawn(world, random, chunkX, chunkZ);

    }

    // FIXME : fix gallion spawn + TODO : add variations to the blocks & (lootables from the structure setblock command)
    // FIXME : populating chunk need to go inside a populate chunk event or biome
    private void processVikingGallionSpawn(World world, Random random, int chunkX, int chunkZ) {
    	if (random.nextInt(100 - SERVER.boat_spawn_rate) == 0) {
    		// trying to center that in the chunk
    		int centerX = chunkX * 16 + 8;
    		int centerZ = chunkZ * 16 + 8;

    		// Start from top and go down to find real surface
    		BlockPos surface = world.getPrecipitationHeight(new BlockPos(centerX, 255, centerZ)).down();
    		IBlockState state = world.getBlockState(surface);
    		
    		Biome biome = world.getBiome(surface);
    		if (biome == null) return;
    		if (!allowedBiomes.contains(biome)) return;
    		if (state.getMaterial() != Material.WATER) return;

    		BlockPos pos = surface; // One block above water

    		if (!world.isAreaLoaded(pos, 16)) return;

    		ChunkPos cp = new ChunkPos(chunkX, chunkZ);
    		if (!alreadyGenerated.add(cp)) return;

    		generateVikingGallion(world, pos, random, new ResourceLocation(Reference.MODID, "viking_gallion"));
    	}
	}

    private void generateVikingGallion(World world, BlockPos pos, Random random, ResourceLocation rl) {
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
            .setIgnoreStructureBlock(true)
            .setReplacedBlock(Blocks.LAPIS_BLOCK);

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
        
//        EntityViking viking = new EntityViking(world);
//        viking.setPositionAndUpdate(pos.getX(), pos.getY() + 10, pos.getZ());
//        world.spawnEntity(viking);
    }


	private void generateNether(World world, Random random, int x, int z)
    {
		this.generateOre(ModBlocks.ALLEMANITE_ORE, Blocks.NETHERRACK, world, random, 4, 4, x, z, 3, 126);
//		generateFlowers(world, random, x, z, 1);
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

    // FIXME: make the flower sustainable checks better...
    private void generateFlowers(World world, Random rand, int x, int z, Biome biomeIn, IBlockState flower) {
    	// Set block state with the random variant
        BlockPos pos = new BlockPos(x + rand.nextInt(16), 0, z + rand.nextInt(16));
        BlockPos topPos = world.getHeight(pos);

        for (int i = 0; i < 4; i++) {
            int offsetX = x + rand.nextInt(16);
            int offsetZ = z + rand.nextInt(16);
            int offsetY = topPos.getY();

            BlockPos flowerPos = new BlockPos(offsetX, offsetY, offsetZ);
            Biome biome = world.getBiome(flowerPos);
            	 if (biome != null && biome == biomeIn) {
            		 if (world.isAirBlock(flowerPos) && ModBlocks.CUSTOM_FLOWER.canPlaceBlockAt(world, flowerPos)) {
            			 world.setBlockState(flowerPos, flower, 2);
            		 }
            	 } else {
            		 return;
            	 }
            	 
             if (world.isAirBlock(flowerPos) && ModBlocks.CUSTOM_FLOWER.canPlaceBlockAt(world, flowerPos)) {
            	 world.setBlockState(flowerPos, flower, 2);
             }
        }
    }

}
