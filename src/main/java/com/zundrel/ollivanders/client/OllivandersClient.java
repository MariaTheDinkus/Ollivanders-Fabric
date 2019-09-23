package com.zundrel.ollivanders.client;

import com.zundrel.ollivanders.client.events.ClientEventHandler;
import com.zundrel.ollivanders.client.keybindings.ModKeybinds;
import com.zundrel.ollivanders.client.render.RenderSpellProjectile;
import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class OllivandersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeybinds.init();

        ClientEventHandler.init();

        EntityRendererRegistry.INSTANCE.register(EntitySpellProjectile.class, ((entityRenderDispatcher, context) -> new RenderSpellProjectile(entityRenderDispatcher)));
    }
}
