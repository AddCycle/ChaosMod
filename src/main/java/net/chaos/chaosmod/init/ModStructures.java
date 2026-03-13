package net.chaos.chaosmod.init;

import net.chaos.chaosmod.world.gen.chaosland.CustomWoodlandMansion;
import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.chaos.chaosmod.world.structures.StructureCustomVillage;
import net.chaos.chaosmod.world.structures.VillageAdditionalStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures {
	public static void registerStructures() {
		MapGenStructureIO.registerStructureComponent(VillageAdditionalStructure.class, "Vas");
		MapGenStructureIO.registerStructure(MapGenCustomVillage.Start.class, "custom_village");
		StructureCustomVillage.registerVillagePieces();
		MapGenStructureIO.registerStructure(CustomWoodlandMansion.Start.class, "Custom Mansion");
	}
}