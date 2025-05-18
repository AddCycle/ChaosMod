package net.chaos.chaosmod.world.structures;

import java.util.List;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureMyRoom extends StructureMyComponent {
	private World world;

    public StructureMyRoom() {}

    public StructureMyRoom(World world, Random rand, BlockPos pos, Rotation rotation) {
        super(pos, rotation == null ? Rotation.NONE : rotation);
        this.world = world;

        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        Template template = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation("chaosmod", "main_room"));
        this.boundingBox = this.getBoundingBoxForCustomTemplate(template, pos, this.rotation);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        TemplateManager manager = worldIn.getSaveHandler().getStructureTemplateManager();
        return placeTemplate(worldIn, manager, new BlockPos(this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ), "room");
    }

    @Override
    public void buildComponent(List<StructureComponent> components, Random rand) {
        // Add a hallway attached to this room, rotated randomly
        Rotation hallwayRotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
        int midX = (this.boundingBox.minX + this.boundingBox.maxX) / 2;
        int midY = (this.boundingBox.minY + this.boundingBox.maxY) / 2;
        int midZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2;
        BlockPos hallwayPos = new BlockPos(midX, midY, midZ);

        StructureMyHallway hallway = new StructureMyHallway(this.world, rand, hallwayPos, hallwayRotation);
        components.add(hallway);
    }

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
		// TODO Auto-generated method stub
		
	}
	
}