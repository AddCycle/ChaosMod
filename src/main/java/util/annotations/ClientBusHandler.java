package util.annotations;

import java.lang.annotation.Annotation;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.lore.dialogs.DialogEventHandler;

public class ClientBusHandler {

	public static void init() {
		Annotation[] annotations = DialogEventHandler.class.getAnnotations();
		for (Annotation a : annotations) {
			if (a instanceof ClientBus) {
				Main.getLogger().info(((ClientBus)a).comment());
			}
		}
	}
}