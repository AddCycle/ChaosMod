package net.chaos.chaosmod.world.structures;

import java.util.Random;

import net.chaos.chaosmod.world.structures.jigsaw.JigsawAssembler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.IWorldGenerator;
import util.Reference;

/**
 * FIXME : this makes cascading worldGenLag but it's fine since it's only in a few surrounding chunks
 */
public class DungeonGenerator implements IWorldGenerator {
	
	private static final PlacementSettings DEFAULT_PLACEMENT = new PlacementSettings();

    // How often to try spawning (1 attempt per N chunks)
    public static final int SPACING = 32;
    private static final int MAX_DEPTH = 15;
	private static final int MAX_RETRIES = 5;
    private static final ResourceLocation STRUCTURE_START = new ResourceLocation(Reference.MODID, "main_room");


    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.isRemote) return;
        if (world.provider.getDimension() != 0) return;

        if (chunkX % SPACING != 0 || chunkZ % SPACING != 0) return;

        if (random.nextInt(3) != 0) return;

        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);

        int surface = world.getHeight(new BlockPos(x, 0, z)).getY();
        int depth = 30 + random.nextInt(20); // 30-50 blocks below the surface
        int y = surface - depth;

        BlockPos pos = new BlockPos(x, y, z);

        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return;

        JigsawAssembler assembler = new JigsawAssembler(STRUCTURE_START, DEFAULT_PLACEMENT);
//        assembler.assemble(server, pos, MAX_DEPTH);
//        assembler.assembleWithRetry(server, pos, MAX_DEPTH, MAX_RETRIES);
        assembler.assemble(server, pos, MAX_DEPTH);
    }
}