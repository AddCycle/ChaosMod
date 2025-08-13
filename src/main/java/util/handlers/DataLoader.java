package util.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class DataLoader {
	private static final Gson GSON = new Gson();

	public static JsonObject loadJson(String pathInAssets) {
        ResourceLocation location = new ResourceLocation("chaosmod", pathInAssets);
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
}
