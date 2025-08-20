package net.chaos.chaosmod.world.gen.overworld;

import java.util.Random;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import util.Reference;

public class WorldGenCustomStructure implements IWorldGenerator {
	public static ResourceLocation CUSTOM_STRUCTURE;
	public int dimension;
	
	public WorldGenCustomStructure(String structureName, int dimension) {
		CUSTOM_STRUCTURE = new ResourceLocation(Reference.MODID, structureName);
		this.dimension = dimension;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
		if (!(world instanceof WorldServer) || world.provider.getDimension() != this.dimension) return;
		
		if (random.nextInt(30) != 0) return;
		
		int x = (chunkX * 16) + random.nextInt(16);
        int z = (chunkZ * 16) + random.nextInt(16);
        
        BlockPos surface = world.getHeight(new BlockPos(x, 0, z));
        
        if (world.getBlockState(surface.down()).getMaterial().isLiquid()) return;
        
        WorldServer ws = (WorldServer) world;
        MinecraftServer server = ws.getMinecraftServer();
        TemplateManager manager = ws.getStructureTemplateManager();

        Template template = manager.getTemplate(server, CUSTOM_STRUCTURE);
        if (template == null) return;
        
        PlacementSettings settings = new PlacementSettings()
                .setMirror(Mirror.NONE)
                .setRotation(Rotation.values()[random.nextInt(Rotation.values().length)])
                .setIgnoreEntities(false);
        
        template.addBlocksToWorld(ws, surface, settings);
	}

}
