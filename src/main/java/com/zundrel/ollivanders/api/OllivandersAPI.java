package com.zundrel.ollivanders.api;

import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.component.IWorldComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class OllivandersAPI {
    public static final String MODID = "ollivanders";
    private static final Logger LOGGER = LogManager.getLogger(MODID + "api");
    public static ArrayList<String> spellNames = new ArrayList<>();

    public static final ComponentType<IPlayerComponent> PLAYER_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "player"), IPlayerComponent.class);
    public static final ComponentType<IWorldComponent> WORLD_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "world"), IWorldComponent.class);

    private OllivandersAPI() {}
}
