package proxy;

import net.chaos.chaosmod.Main;
import net.minecraft.item.Item;

/**
 * Should only be implemented by Item subclasses
 */
public interface IItemModel {
	default public void registerModels() {
		Main.proxy.registerItemRenderer((Item) this, 0, "inventory");
	}
}
