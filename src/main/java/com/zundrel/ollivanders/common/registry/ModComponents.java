package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.common.component.PlayerComponent;
import com.zundrel.ollivanders.common.component.WorldComponent;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import nerdhub.cardinal.components.api.util.EntityComponents;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;

public class ModComponents {
    public static void init() {
        EntityComponentCallback.event(PlayerEntity.class).register((player, components) -> components.put(OllivandersAPI.PLAYER_COMPONENT, new PlayerComponent(player)));
        EntityComponents.setRespawnCopyStrategy(OllivandersAPI.PLAYER_COMPONENT, RespawnCopyStrategy.ALWAYS_COPY);
        WorldComponentCallback.EVENT.register((world, components) -> components.put(OllivandersAPI.WORLD_COMPONENT, new WorldComponent(world)));
    }
}
