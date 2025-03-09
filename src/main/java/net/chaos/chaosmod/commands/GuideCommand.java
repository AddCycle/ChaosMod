package net.chaos.chaosmod.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import util.guide.model.block.GuideBlock;
import util.jsonreader.JsonReader;

public class GuideCommand extends CommandBase {

	@Override
	public String getName() {
		return "guide";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide <resource_path> [page]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		String path = "assets/chaosmod/models/block/info/oxonium_ore.info";
		JsonReader reader = new JsonReader();
		System.out.println("Content of the file : " + reader.getFileContentFromResourceString(path));
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(reader.getFileContentFromResourceString(path)).getAsJsonObject();

		if (args.length == 1) {
			path = args[0];
		}
		sender.sendMessage(new TextComponentString("Item/Block name : " + object.get("name")));
		sender.sendMessage(new TextComponentString("Item/Block type : " + object.get("type")));
		sender.sendMessage(new TextComponentString("Item/Block description : " + object.get("description")));
		sender.sendMessage(new TextComponentString("Item/Block texture : " + object.get("texture")));
		System.out.println("Item/Block name : " + object.get("name"));
		System.out.println("Item/Block type : " + object.get("type"));
		System.out.println("Item/Block description : " + object.get("description"));
		System.out.println("Item/Block texture : " + object.get("texture"));
	}

}
