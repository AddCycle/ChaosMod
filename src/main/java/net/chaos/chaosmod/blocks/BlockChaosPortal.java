package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.commands.TeleportUtil;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockChaosPortal extends BlockEndPortal {

    public BlockChaosPortal(String name) {
        super(Material.PORTAL);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(1.0F);
        
        ModBlocks.BLOCKS.add(this);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!world.isRemote && entityIn instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entityIn;
            MinecraftServer server = player.getServer();
            if (server != null && player.timeUntilPortal <= 0) {
                player.timeUntilPortal = 100;

                int customDimId = world.provider.getDimension() == 0 ? ModDimensions.customId : 0;
                WorldServer targetWorld = server.getWorld(customDimId);

                if (targetWorld != null) {
                	BlockPos spawn = targetWorld.getSpawnPoint();
                	int x = spawn.getX();
                	int z = spawn.getZ();
                	int y = targetWorld.getHeight(x,z);
                	TeleportUtil.teleport(player, customDimId, x, y, z);
                }
            }
        }
    }
}