package com.zundrel.ollivanders.api.utils;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.component.IWorldComponent;
import nerdhub.cardinal.components.api.component.ComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class OllivandersComponents {
    public static IPlayerComponent getPlayerComponent(PlayerEntity playerEntity) {
        return ComponentProvider.fromEntity(playerEntity).getComponent(OllivandersAPI.PLAYER_COMPONENT);
    }

    public static IWorldComponent getWorldComponent(World world) {
        return ComponentProvider.fromWorld(world).getComponent(OllivandersAPI.WORLD_COMPONENT);
    }
}
