package proxy;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * Should only be implemented by Block subclasses
 */
public interface IBlockModel {

    default void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock((Block) this), 0, "inventory");
    }

}