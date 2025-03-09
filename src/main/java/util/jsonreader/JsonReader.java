package util.jsonreader;

import java.io.InputStream;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import util.guide.model.block.GuideBlock;

public class JsonReader {
	public String getFileContentFromResourceString(String filename) {
		// filename = "assets/chaosmod/models/block/info/oxonium_ore.json"
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream(filename);
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	
	public static GuideBlock getJsonAsObject(String content) {
		Gson g = new Gson();
		return g.fromJson(content, GuideBlock.class);
	}
}
