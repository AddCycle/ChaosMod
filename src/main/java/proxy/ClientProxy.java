package proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

// Adds a simple mapping from Item + metadata to the model variant. (en gros il enregistre les objets dans la structure du loader pour afficher correctement les objets)
public class ClientProxy extends CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
}
