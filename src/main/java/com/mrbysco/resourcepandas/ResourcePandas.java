package com.mrbysco.resourcepandas;

import com.mrbysco.resourcepandas.client.ClientHandler;
import com.mrbysco.resourcepandas.entity.ResourcePandaEntity;
import com.mrbysco.resourcepandas.handler.ConversionHandler;
import com.mrbysco.resourcepandas.item.PandaDataComponents;
import com.mrbysco.resourcepandas.recipe.PandaRecipes;
import com.mrbysco.resourcepandas.registry.PandaRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class ResourcePandas {
	public static final Logger LOGGER = LogManager.getLogger();

	public ResourcePandas(IEventBus eventBus, Dist dist) {
		NeoForge.EVENT_BUS.register(new ConversionHandler());

		PandaRegistry.ENTITY_TYPES.register(eventBus);
		PandaDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
		PandaRegistry.ITEMS.register(eventBus);
		PandaRegistry.CREATIVE_MODE_TABS.register(eventBus);
		PandaRecipes.RECIPE_TYPES.register(eventBus);
		PandaRecipes.RECIPE_SERIALIZERS.register(eventBus);

		eventBus.addListener(this::registerEntityAttributes);

		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerItemColors);
		}
	}

	public void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(PandaRegistry.RESOURCE_PANDA.get(), ResourcePandaEntity.genAttributeMap().build());
	}
}
