package util.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class DataLoader {
	private static final Gson GSON = new Gson();

	public static JsonObject loadJsonFromClient(String pathInAssets) {
        ResourceLocation location = new ResourceLocation(Reference.MODID, pathInAssets);
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();

        try (IResource resource = resourceManager.getResource(location);
             InputStream inputStream = resource.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            return GSON.fromJson(reader, JsonObject.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static JsonObject loadServerJson(String pathInAssets) {
		String fullPath = "/assets/" + Reference.MODID + "/" + pathInAssets;

	    try (InputStream stream = DataLoader.class.getResourceAsStream(fullPath)) {
	        if (stream == null) {
	            throw new FileNotFoundException("Could not find server resource: " + fullPath);
	        }
	        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
	            return GSON.fromJson(reader, JsonObject.class);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}