package util.guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import util.guide.model.block.GuideBlock;

public class GenerateGuideContent {
	public String path;
	
	public GenerateGuideContent() {}

	public String getFileContent(String path) throws IOException {
		this.path = "assets/chaosmod/guide/info/blocks/" + path + ".info";
		System.out.println("PATH : " + this.path);
		InputStream is = getClass().getClassLoader().getResourceAsStream(this.path);
		StringBuilder result = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line).append("\n");
			}
		}
		System.out.println("Content of the file from method : " + result.toString());
		System.out.println("Path of the file : " + this.path);
		return result.toString();
	}
	
	public GuideBlock getGuideBlock(String json_content) throws IOException {
		Gson g = new Gson();
		GuideBlock guide_block = g.fromJson(json_content, GuideBlock.class);
		return guide_block;
	}
	
}
