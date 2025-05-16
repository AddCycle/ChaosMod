package net.chaos.chaosmod.world.gen.procedural;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import util.Reference;

public class DungeonGenerator {

    /*private static final int MAX_DEPTH = 4;

    public static void generate(World world, BlockPos origin) {
    	System.out.println("Generating 2 ?");
        generateRecursive(world, origin, "main_room", 0, new HashSet<>());
    }*/

    /*private static void generateRecursive(World world, BlockPos pos, String structureName, int depth, Set<BlockPos> placedPositions) {
    	System.out.println("Generating 3 ?");
    	if (depth >= MAX_DEPTH) return;

    	Template template = loadTemplate(world, structureName);
    	if (template == null) return;

    	PlacementSettings settings = new PlacementSettings()
    	    .setMirror(Mirror.NONE)
    	    .setRotation(Rotation.NONE)
    	    .setIgnoreEntities(false);

    	// Place structure
    	template.addBlocksToWorld(world, pos, settings);

    	// Get connection points *only* from template itself
    	List<BlockPos> connections = new ArrayList<>();
    	for () {
    	    if (blockInfo.blockState.getBlock() == Blocks.LAPIS_BLOCK) {
    	        BlockPos worldPos = pos.add(blockInfo.pos);
    	        connections.add(worldPos.up());
    	        world.setBlockToAir(worldPos);
    	    }
    	}

    	for (BlockPos conn : connections) {
    	    if (isTooClose(placedPositions, conn, 12)) continue;
    	    placedPositions.add(conn);

    	    String[] nextStructures = new String[]{"hallway", "fight_room"};
    	    String next = nextStructures[world.rand.nextInt(nextStructures.length)];

    	    generateRecursive(world, conn, next, depth + 1, placedPositions);
    	}
    }*/
    
    // version 2
    /*private static void generateRecursive(World world, BlockPos pos, String structureName, int depth, Set<BlockPos> placedPositions) {
        System.out.println("Generating 3 ?");
        if (depth >= MAX_DEPTH) return;

        Template template = loadTemplate(world, structureName);
        if (template == null) return;

        PlacementSettings settings = new PlacementSettings()
            .setMirror(Mirror.NONE)
            .setRotation(Rotation.NONE)
            .setIgnoreEntities(false);

        template.addBlocksToWorld(world, pos, settings);

        BlockPos size = template.getSize();
        List<BlockPos> connections = findConnectionPoints(world, pos, size);

        for (BlockPos conn : connections) {
            if (isTooClose(placedPositions, conn, 12)) continue;
            placedPositions.add(conn);

            String[] nextStructures = new String[]{"hallway", "fight_room"};
            String next = nextStructures[world.rand.nextInt(nextStructures.length)];

            generateRecursive(world, conn, next, depth + 1, placedPositions);
        }
    }*/
    
    /*private static void generateRecursive(World world, BlockPos pos, String structureName, int depth, Set<BlockPos> placedPositions) {
        if (depth > MAX_DEPTH) return;

        Template template = loadTemplate(world, structureName);
        if (template == null) return;

        Rotation rotation = randomRotation(world); // ✅ Random rotation

        PlacementSettings settings = new PlacementSettings()
            .setMirror(Mirror.NONE)
            .setRotation(rotation)
            .setIgnoreEntities(false);

        template.addBlocksToWorld(world, pos, settings);

        BlockPos size = template.getSize();
        List<BlockPos> connections = findConnectionPoints(world, pos, size, rotation);

        for (BlockPos conn : connections) {
            if (isTooClose(placedPositions, conn, 8)) continue;
            placedPositions.add(conn);

            String[] nextStructures = new String[]{"hallway", "fight_room"};
            String next = nextStructures[world.rand.nextInt(nextStructures.length)];

            generateRecursive(world, conn, next, depth + 1, placedPositions);
        }
    }


    private static Template loadTemplate(World world, String name) {
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return null;

        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        ResourceLocation rl = new ResourceLocation(Reference.MODID, name); // maybe let the structure/ part before name
        return manager.getTemplate(server, rl);
    }*/

    // version 1
    /* private static List<BlockPos> findConnectionPoints(World world, BlockPos start, BlockPos size) {
        List<BlockPos> points = new ArrayList<>();

        BlockPos end = start.add(size).add(-1, -1, -1); // Fix: inclusive end
        Iterable<MutableBlockPos> area = BlockPos.getAllInBoxMutable(start, end);

        for (BlockPos pos : area) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.LAPIS_BLOCK) {
                points.add(pos.up()); // Connect above the block
                world.setBlockToAir(pos); // Clean up the block
            }
        }

        return points;
    }*/
    
    /*private static List<BlockPos> findConnectionPoints(World world, BlockPos start, BlockPos size, Rotation rotation) {
        List<BlockPos> points = new ArrayList<>();
        BlockPos end = start.add(size).add(-1, -1, -1);

        for (BlockPos pos : BlockPos.getAllInBoxMutable(start, end)) {
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() == Blocks.LAPIS_BLOCK) {
                world.setBlockToAir(pos); // Clean up marker
                points.add(pos.up());
            }
        }

        return points;
    }

    
    private static boolean isTooClose(Set<BlockPos> placedPositions, BlockPos newPos, int minDistance) {
        for (BlockPos existing : placedPositions) {
            if (existing.distanceSq(newPos) < minDistance * minDistance)
                return true;
        }
        return false;
    }
    
    private static Rotation randomRotation(World world) {
        Rotation[] rotations = Rotation.values();
        return rotations[world.rand.nextInt(rotations.length)];
    }*/
	
	/*private static final int MAX_DEPTH = 5;

    public static void generate(World world, BlockPos origin) {
        generateRecursive(world, origin, "main_room", EnumFacing.NORTH, 0, new HashSet<>());
    }*/

    // other ver.
    /* private static void generateRecursive(World world, BlockPos origin, String structureName, EnumFacing incomingDirection, int depth, Set<BlockPos> occupied) {
        if (depth > MAX_DEPTH) return;

        for (Rotation rotation : Rotation.values()) {
            Template template = loadTemplate(world, structureName);
            if (template == null) return;

            PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation);
            List<Connector> connectors = findConnectors(template, settings, pos);

            for (Connector connector : connectors) {
                if (placedPositions.contains(connector.pos)) continue;
                placedPositions.add(connector.pos);

                // Choose structure
                String[] nextStructures = new String[]{"hallway", "fight_room"};
                String next = nextStructures[world.rand.nextInt(nextStructures.length)];

                // Compute matching rotation (you may enhance this logic)
                Rotation rotation = getMatchingRotation(connector.facing);

                PlacementSettings nextSettings = new PlacementSettings()
                    .setMirror(Mirror.NONE)
                    .setRotation(rotation)
                    .setIgnoreEntities(false);

                Template nextTemplate = loadTemplate(world, next);
                if (nextTemplate == null) continue;

                BlockPos offset = getConnectorOffset(nextTemplate, nextSettings, connector.facing.getOpposite());

                BlockPos nextPos = connector.pos.add(offset);

                generateRecursive(world, nextPos, next, depth + 1, placedPositions);
            }
        }
    }*/

    private static Template loadTemplate(World world, String name) {
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) return null;
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        ResourceLocation rl = new ResourceLocation(Reference.MODID, name);
        return manager.getTemplate(server, rl);
    }

    private static List<Connector> findConnectors(Template template, PlacementSettings settings, BlockPos origin) {
        List<Connector> connectors = new ArrayList<>();
        Map<BlockPos, String> dataBlocks = template.getDataBlocks(BlockPos.ORIGIN, settings);

        for (Map.Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
            BlockPos relativePos = entry.getKey();
            String metadata = entry.getValue();

            if (metadata.startsWith("connector:")) {
                String dir = metadata.substring("connector:".length());
                EnumFacing facing = EnumFacing.byName(dir.toLowerCase());

                if (facing != null) {
                    BlockPos worldPos = relativePos.add(origin); // origin is where the structure is placed in the world
                    connectors.add(new Connector(worldPos, facing));
                }
            }
        }
        return connectors;
    }

    private static class Connector {
        BlockPos localPos;
        EnumFacing facing;
        Connector(BlockPos pos, EnumFacing facing) {
            this.localPos = pos;
            this.facing = facing;
        }
    }
}