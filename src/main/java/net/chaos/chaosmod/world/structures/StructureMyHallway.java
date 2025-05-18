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

public class StructureMyHallway extends StructureMyComponent {

    public StructureMyHallway() {}

    public StructureMyHallway(World world, Random rand, BlockPos pos, Rotation rotation) {
        super(pos, rotation);
        TemplateManager manager = world.getSaveHandler().getStructureTemplateManager();
        Template template = manager.getTemplate(world.getMinecraftServer(), new ResourceLocation("chaosmod", "hallway"));
        this.boundingBox = getBoundingBoxForCustomTemplate(template, pos, this.rotation);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
        TemplateManager manager = worldIn.getSaveHandler().getStructureTemplateManager();
        return placeTemplate(worldIn, manager, new BlockPos(this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ), "hallway");
    }

    @Override
    public void buildComponent(List<StructureComponent> components, Random rand) {
        // You can add more linked pieces here if you want a longer hallway or branching
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