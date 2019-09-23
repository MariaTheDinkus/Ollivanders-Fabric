package com.zundrel.ollivanders.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Callback for sending a message.
 * Called directly before a message is sent.
 * Return the radius you want messages to be heard from.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal message behavior.
 * - PASS cancels further processing and continues with normal message behavior.
 * - FAIL cancels further processing and does not send the message.
 **/
public interface ServerChatCallback {
    Event<ServerChatCallback> EVENT = EventFactory.createArrayBacked(ServerChatCallback.class, (listeners) -> (player, message) -> {
        for (ServerChatCallback event : listeners) {
            return event.interact(player, message);
        }
        return -1;
    });

    int interact(ServerPlayerEntity player, String message);
}
