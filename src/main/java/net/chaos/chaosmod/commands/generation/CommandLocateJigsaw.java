package net.chaos.chaosmod.commands.generation;

import java.util.Random;

import javax.annotation.Nullable;

import net.chaos.chaosmod.world.structures.DungeonGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CommandLocateJigsaw extends CommandBase {

    @Override
    public String getName() { return "locatejigsaw"; }

    @Override
    public String getUsage(ICommandSender sender) { return "/locatejigsaw [searchRadius]"; }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = sender.getEntityWorld();
        BlockPos senderPos = sender.getPosition();

        int searchRadius = args.length >= 1 ? parseInt(args[0], 1, 512) : 256;

        BlockPos result = findNearestJigsaw(world, senderPos, searchRadius);

        if (result == null) {
            sender.sendMessage(new TextComponentString(
                "No jigsaw structure found within " + searchRadius + " blocks."
            ));
        } else {
            double dist = Math.sqrt(senderPos.distanceSq(result.getX(), senderPos.getY(), result.getZ()));
            sender.sendMessage(new TextComponentString(String.format(
                "Nearest jigsaw structure at: %d ? %d (%.1f blocks away)",
                result.getX(), result.getZ(), dist
            )));
        }
    }

    private static Random getChunkRandom(World world, int chunkX, int chunkZ) {
        long worldSeed = world.getSeed();
        Random fmlRandom = new Random(worldSeed);
        long xSeed = fmlRandom.nextLong() >> 2 + 1L;
        long zSeed = fmlRandom.nextLong() >> 2 + 1L;
        long chunkSeed = (xSeed * chunkX + zSeed * chunkZ) ^ worldSeed;
        fmlRandom.setSeed(chunkSeed);

        return fmlRandom;
    }

    @Nullable
    private BlockPos findNearestJigsaw(World world, BlockPos origin, int searchRadius) {
        int originChunkX = origin.getX() >> 4;
        int originChunkZ = origin.getZ() >> 4;
        int chunkRadius = (searchRadius >> 4) + DungeonGenerator.SPACING;

        BlockPos nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (int r = 0; r <= chunkRadius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) != r && Math.abs(dz) != r) continue;

                    int chunkX = originChunkX + dx;
                    int chunkZ = originChunkZ + dz;

                    if (chunkX % DungeonGenerator.SPACING != 0) continue;
                    if (chunkZ % DungeonGenerator.SPACING != 0) continue;

                    Random random = getChunkRandom(world, chunkX, chunkZ);

                    if (random.nextInt(3) != 0) continue;
                    int x = chunkX * 16 + random.nextInt(16);
                    int z = chunkZ * 16 + random.nextInt(16);
                    int surface = world.getHeight(new BlockPos(x, 0, z)).getY();
                    int depth = 30 + random.nextInt(20);
                    int y = surface - depth;

                    BlockPos candidate = new BlockPos(x, y, z);
                    double dist = origin.distanceSq(candidate.getX(), origin.getY(), candidate.getZ());

                    if (dist < nearestDist) {
                        nearestDist = dist;
                        nearest = candidate;
                    }
                }
            }
        }

        return nearest;
    }
}