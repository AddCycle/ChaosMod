package net.chaos.chaosmod.commands.generation;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class LoadStructCommand extends CommandBase {
	@Override
	public String getName() {
		return "template_load";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/template_load <name>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
		if (args.length != 1) {
			notifyCommandListener(sender, this, "Usage: /template_load <name>");
			return;
		}

		World world = sender.getEntityWorld();
		BlockPos pos = sender.getPosition().add(0, 0, 5); // Spawn 5 blocks in front

		ResourceLocation templateName = new ResourceLocation("chaosmod", args[0]);
		TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();
		Template template = manager.getTemplate(world.getMinecraftServer(), templateName);

		if (template == null || template.getSize().equals(BlockPos.ORIGIN)) {
			notifyCommandListener(sender, this, "Template not found or empty: " + templateName);
			return;
		}

		PlacementSettings settings = new PlacementSettings()
				.setMirror(Mirror.NONE)
				// .setRotation(Rotation.NONE)
				.setRotation(Rotation.CLOCKWISE_90)
				.setIgnoreEntities(false)
				.setChunk(null)
				.setReplacedBlock(Blocks.LAPIS_BLOCK)
				.setIgnoreStructureBlock(false);

		template.addBlocksToWorld(world, pos, settings);

		notifyCommandListener(sender, this, "Template loaded: " + templateName + " at " + pos);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
}
