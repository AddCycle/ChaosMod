package net.chaos.chaosmod.blocks.complex.mimic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class MimicStartupClient {
	public static void preInitClientOnly()
	  {
	    StateMapperBase ignoreState = new StateMapperBase() {
	      @Override
	      protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
	        return ShapeShiftingBakedModel.variantTag;
	      }
	    };
	    ModelLoader.setCustomStateMapper(StartupCommon.blockCamouflage, ignoreState);
	    // NB If your block has multiple variants and you want vanilla to load a model for each variant, you don't need a
	    //   custom state mapper.
	    // You can see examples of vanilla custom state mappers in BlockModelShapes.registerAllBlocks()

	    // ModelBakeEvent will be used to add our CamouflageBakedModel to the ModelManager's registry (the
	    //  registry used to map all the ModelResourceLocations to IBlockModels).  For the stone example there is a map from
	    // ModelResourceLocation("minecraft:granite#normal") to an IBakedModel created from models/block/granite.json.
	    // For the camouflage block, it will map from
	    // CamouflageBakedModel.modelResourceLocation to our CamouflageBakedModel instance
	    MinecraftForge.EVENT_BUS.register(ModelBakeEventHandler.instance);

	    // This step is necessary in order to make your block render properly when it is an item (i.e. in the inventory
	    //   or in your hand or thrown on the ground).
	    // It must be done on client only, and must be done after the block has been created in Common.preinit().
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("chaosmod:shapeshifter_block", "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    ModelLoader.setCustomModelResourceLocation(StartupCommon.itemBlockCamouflage, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
	  }

}
