package util.guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import net.chaos.chaosmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.guide.model.block.GuideBlock;

public class GenerateGuideContent {
	public String path;
	
	public GenerateGuideContent() {}

	/*public String getFileContent(String path) throws IOException {
		this.path = "assets/chaosmod/guide/info/blocks/" + path + ".info";
		InputStream is = GenerateGuideContent.class.getClassLoader().getResourceAsStream(this.path);
		System.out.println("PATH : " + this.path);
		StringBuilder result = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line).append("\n");
			}
		}
		return result.toString();
	}
	
	public GuideBlock getGuideBlock(String json_content) throws IOException {
		Gson g = new Gson();
		GuideBlock guide_block = g.fromJson(json_content, GuideBlock.class);
		return guide_block;
	}*/
	
}
