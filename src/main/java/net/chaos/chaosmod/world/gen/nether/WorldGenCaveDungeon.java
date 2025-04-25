package net.chaos.chaosmod.world.gen.nether;

import java.util.Random;

import net.chaos.chaosmod.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCaveDungeon extends WorldGenerator implements IWorldGenerator {

    private static final ResourceLocation STRUCTURE = new ResourceLocation("chaosmod:nether_dungeon");

    @Override
    public boolean generate(World world, Random rand, BlockPos pos) {
        if (!(world instanceof WorldServer)) return false;
        if (world.provider.getDimension() != -1) return false; // Only Nether

        // Check that area is surrounded (like a mini cave)
        if (!isCaveLike(world, pos)) return false;
        System.out.println("Generating around : " + pos);
        
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return false;

        // Load the structure
        TemplateManager manager = ((WorldServer)world).getStructureTemplateManager();
        Template template = manager.getTemplate(server, STRUCTURE);

        if (template == null || template.getSize().equals(BlockPos.ORIGIN)) return false;

        // Add settings: Mirror/rotation/etc
        PlacementSettings settings = new PlacementSettings()
            .setIgnoreEntities(false)
            .setChunk(null)
            .setReplacedBlock(null)
            .setIgnoreStructureBlock(false);

        BlockPos spawnPos = pos.add(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);
        template.addBlocksToWorldChunk(world, spawnPos, settings);

        return true;
    }

    private boolean isCaveLike(World world, BlockPos center) {
        int airCount = 0;
        int total = 0;

        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    check.setPos(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    IBlockState state = world.getBlockState(check);
                    total++;
                    if (state.getMaterial().isReplaceable()) airCount++;
                }
            }
        }

        float airRatio = airCount / (float) total;
        return airRatio > 0.2f && airRatio < 0.6f; // Between 20%–60% cave-like
    }

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == -1) { // Only in the Nether
		    int x = chunkX * 16 + random.nextInt(16);
		    int z = chunkZ * 16 + random.nextInt(16);
		    int y = 32 + random.nextInt(32); // Try some depth, avoid lava lake layer

		    BlockPos pos = new BlockPos(x, y, z);

		    // Try a few positions per chunk to increase spawn chance
		    for (int i = 0; i < 3; i++) {
		        if (generate(world, random, pos.add(random.nextInt(8), random.nextInt(8), random.nextInt(8)))) {
		            System.out.println("Structure generated at: " + pos);
		        }
		    }
		}
	}
}