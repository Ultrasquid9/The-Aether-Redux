package net.zepalesque.redux.compat.jade;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.Map;
import java.util.function.Supplier;

public enum ReduxNotices implements IEntityComponentProvider, IBlockComponentProvider {

    INSTANCE;

    private static final Map<EntityType<?>, Supplier<Boolean>> ENTITIES = new ImmutableMap.Builder<EntityType<?>, Supplier<Boolean>>()
            .put(AetherEntityTypes.MOA.get(), ReduxConfig.CLIENT.moa_improvements)
            .put(AetherEntityTypes.COCKATRICE.get(), ReduxConfig.CLIENT.cockatrice_improvements)
            .put(AetherEntityTypes.SENTRY.get(), ReduxConfig.CLIENT.sentry_improvements)
            .build();

    private static final Component ENTITY_TOOLTIP = Component.translatable("gui.aether_redux.jade.entity_model");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        EntityType<?> type = entityAccessor.getEntity().getType();
        if (ENTITIES.containsKey(type) && ENTITIES.get(type).get()) {
            iTooltip.add(ENTITY_TOOLTIP);
        }
    }

    private static final ResourceLocation id = Redux.locate("modification_notices");

    @Override
    public ResourceLocation getUid() {
        return id;
    }



    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

    }
}
