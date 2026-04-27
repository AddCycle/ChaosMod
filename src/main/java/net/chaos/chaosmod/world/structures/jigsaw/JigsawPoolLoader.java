package net.chaos.chaosmod.world.structures.jigsaw;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.chaos.chaosmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import util.Reference;

// TODO : later fix & use
public class JigsawPoolLoader {
	private static final Gson GSON = new GsonBuilder().create();

    // F3+T reload call
    public static void loadAll() {
        JigsawPoolRegistry.clear();

        // Scan all assets for pool JSON files
        for (String domain : Loader.instance().getActiveModList()
                .stream().map(m -> m.getModId()).collect(Collectors.toList())) {

            loadFromDomain(domain);
        }
    }

    private static void loadFromDomain(String domain) {
        // Walk all files in assets/modid/structures/pools/ recursively
        try {
            IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
            // getAllResources with a prefix to find all pool jsons
            String prefix = "structures/pools";
            Collection<IResource> resources = rm.getAllResources(new ResourceLocation(Reference.MODID, prefix + "/" + domain));

        } catch (Exception e) {
            Main.getLogger().error("Failed to load jigsaw pools for domain: " + domain, e);
        }
    }
}