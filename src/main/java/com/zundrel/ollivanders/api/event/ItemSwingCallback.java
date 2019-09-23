package com.zundrel.ollivanders.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

/**
 * Callback for swinging an item.
 * Called directly after an item is swung at either a block or air.
 * Upon return:
 * - SUCCESS cancels further processing and continues with normal swinging behavior.
 * - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 * - FAIL still continues further processing and still swings the held item.
 **/
public interface ItemSwingCallback {
    Event<ItemSwingCallback> EVENT = EventFactory.createArrayBacked(ItemSwingCallback.class, (listeners) -> (player) -> {
        for (ItemSwingCallback event : listeners) {
            ActionResult result = event.interact(player);
            if (result != ActionResult.PASS) {
                return ActionResult.PASS;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult interact(PlayerEntity player);
}
