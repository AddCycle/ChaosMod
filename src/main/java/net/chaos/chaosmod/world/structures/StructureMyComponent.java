package net.chaos.chaosmod.world.structures;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract class StructureMyComponent extends StructureComponent {

    protected Rotation rotation = Rotation.NONE;

    public StructureMyComponent() {}

    public StructureMyComponent(BlockPos pos, Rotation rotation) {
        this.rotation = rotation;
        this.setCoordBaseMode(EnumFacing.NORTH);  // not important here
    }

    /**
     * Loads a structure template and places it in the world with correct rotation.
     * Call this inside addComponentParts.
     */
    protected boolean placeTemplate(World world, TemplateManager manager, BlockPos pos, String templateName) {
        Template template = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation("yourmod", templateName));
        if (template == null) return false;

        PlacementSettings settings = (new PlacementSettings())
            .setRotation(this.rotation)
            .setMirror(net.minecraft.util.Mirror.NONE)
            .setChunk(null)
            .setIgnoreEntities(false)
            .setReplacedBlock(null);

        template.addBlocksToWorld(world, pos, settings);
        return true;
    }

    /**
     * Helper to get the rotated bounding box of a template.
     * Use in constructor to set this.boundingBox.
     */
    protected StructureBoundingBox getBoundingBoxForTemplate(Template template, BlockPos pos, EnumFacing rotation) {
        StructureBoundingBox bb = StructureBoundingBox.getComponentToAddBoundingBox(
            pos.getX(), pos.getY(), pos.getZ(),
            0, 0, 0,
            template.getSize().getX(),
            template.getSize().getY(),
            template.getSize().getZ(),
            rotation
        );
        return bb;
    }

    // Abstract method to link other components
    public abstract void buildComponent(List<StructureComponent> components, Random rand);

	protected StructureBoundingBox getBoundingBoxForCustomTemplate(Template template, BlockPos pos, Rotation rotation) {
	    BlockPos size = template.getSize().rotate(rotation);
	    return new StructureBoundingBox(
	        pos.getX(),
	        pos.getY(),
	        pos.getZ(),
	        pos.getX() + size.getX(),
	        pos.getY() + size.getY(),
	        pos.getZ() + size.getZ()
	    );
	}
}