package net.chaos.chaosmod.commands;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import util.Reference;
import util.guide.GenerateGuideContent;
import util.guide.model.block.GuideBlock;

public class GuideCommand extends CommandBase {

	@Override
	public String getName() {
		return "guide";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide <item_id> (example -> chaosmod:allemanite_ore) [page (development phase)]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		String item_id = null;
		if (args.length == 1) {
			item_id = args[0];
			System.out.println("ITEM_ID before path processing = " + item_id);
		}
		System.out.println("substring : " + item_id.substring(0, 9));
		if (item_id.substring(0, 9).equals(Reference.MODID + ":")) {
			item_id = item_id.substring(9);
			System.out.println("ITEM_ID after command = " + item_id);
		}
		GenerateGuideContent util = new GenerateGuideContent();
		String content = null;
		try {
			content = util.getFileContent(item_id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("The item_id : " + item_id);
		GuideBlock ore_guide = null;
		try {
			ore_guide = util.getGuideBlock(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(new TextComponentString("Name : " + ore_guide.getName()));
		sender.sendMessage(new TextComponentString("Type : " + ore_guide.getType()));
		sender.sendMessage(new TextComponentString("Description : " + ore_guide.getDescription()));
		sender.sendMessage(new TextComponentString("Texture : " + ore_guide.getTexture()));
	}

}
