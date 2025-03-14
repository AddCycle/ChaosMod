package net.chaos.chaosmod.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.network.ChaosModPacketHandler;
import net.chaos.chaosmod.network.GuideCommandMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import proxy.ClientProxy;
import util.Reference;
import util.guide.GenerateGuideContent;
import util.guide.model.block.GuideBlock;

public class GuideCommand extends CommandBase implements ICommand {
	public List<String> aliases = new ArrayList<String>();
	public List<String> completion = new ArrayList<String>();
	public static boolean is_open = false;

	@Override
	public String getName() {
		return "guide";
	}
	
	@Override
    public List<String> getAliases()
    {
        aliases.add("gui");
        return aliases;
    }

	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		if (completion.isEmpty()) {
			for (Item i : ModItems.ITEMS) {
				this.completion.add(i.getRegistryName().toString());
			}
		}
		return this.completion;
    }

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide <item_id> (example -> chaosmod:allemanite_ore) [page (development phase)]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		// EntityPlayer player_client = FMLClientHandler.instance().getClientPlayerEntity();
		// WorldClient world = Minecraft.getMinecraft().world;
		// World world = FMLClientHandler.instance().getWorldClient();
		World world = player.world;
		String item_id = null;
		if (args.length == 1) {
			item_id = args[0];
		} else {
			item_id = "chaosmod:oxonium_ore";
		}
		System.out.println("substring : " + item_id.substring(0, 9));
		if (item_id.substring(0, 9).equals(Reference.MODID + ":")) {
			item_id = item_id.substring(9);
		}
		GenerateGuideContent util = new GenerateGuideContent();
		String content = null;
		try {
			content = util.getFileContent(item_id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GuideBlock ore_guide = null;
		try {
			ore_guide = util.getGuideBlock(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.sendMessage(new TextComponentString("Name : " + ore_guide.getName()));
		player.sendMessage(new TextComponentString("Type : " + ore_guide.getType()));
		player.sendMessage(new TextComponentString("Description : " + ore_guide.getDescription()));
		player.sendMessage(new TextComponentString("Texture : " + ore_guide.getTexture()));
		if (!world.isRemote) {
			// player_client.openGui(Main.instance, Reference.GUI_GUIDE_ID, world, 0, 0, 0);
			// player.openGui(Main.instance, Reference.GUI_GUIDE_ID, world, 0, 0, 0);
			Main.network.sendTo(new GuideCommandMessage(), player);
			player.sendMessage(new TextComponentString("good : not remote"));
			is_open = true;
		}
		player.sendMessage(new TextComponentString("gui opened"));
	}
}
