package net.chaos.chaosmod.init;

import net.chaos.chaosmod.world.structures.StructureMyHallway;
import net.chaos.chaosmod.world.structures.StructureMyRoom;
import net.chaos.chaosmod.world.structures.StructureMyStructureStart;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures {
	public static void registerStructures() {
		MapGenStructureIO.registerStructure(StructureMyStructureStart.class, "MyStructure");
        MapGenStructureIO.registerStructureComponent(StructureMyRoom.class, "MyRoom");
        MapGenStructureIO.registerStructureComponent(StructureMyHallway.class, "MyHallway");
    }

}
