package com.mrbysco.resourcepandas.client;

import com.mrbysco.resourcepandas.client.renderer.ResourcePandaRenderer;
import com.mrbysco.resourcepandas.item.PandaSpawnEggItem;
import com.mrbysco.resourcepandas.registry.PandaRegistry;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ClientHandler {
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(PandaRegistry.RESOURCE_PANDA.get(), ResourcePandaRenderer::new);
	}

	public static void registerItemColors(final RegisterColorHandlersEvent.Item event) {
		PandaSpawnEggItem pandaEgg = (PandaSpawnEggItem) PandaRegistry.RESOURCE_PANDA_SPAWN_EGG.get();
		event.register((stack, tintIndex) -> FastColor.ARGB32.opaque(pandaEgg.getColor(stack, tintIndex)), pandaEgg);
	}
}
