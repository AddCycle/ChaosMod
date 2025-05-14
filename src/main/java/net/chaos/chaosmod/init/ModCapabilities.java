package net.chaos.chaosmod.init;

import net.chaos.chaosmod.client.inventory.AccessoryImpl;
import net.chaos.chaosmod.client.inventory.AccessoryStorage;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
	@CapabilityInject(IAccessory.class)
    public static final Capability<IAccessory> ACCESSORY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(
            IAccessory.class,
            new AccessoryStorage(),
            AccessoryImpl::new
        );
    }

}
