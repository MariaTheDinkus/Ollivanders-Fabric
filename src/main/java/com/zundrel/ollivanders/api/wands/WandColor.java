package com.zundrel.ollivanders.api.wands;

import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.item.Item;

public class WandColor {
    public static void setColor(Item item) {
        ColorProviderRegistry.ITEM.register((stack, layer) -> {
            if (!stack.isEmpty() && stack.getItem() instanceof IWandItem) {
                return ((IWandItem) stack.getItem()).getWood().getColor();
            }

            return 0xFFFFFF;
        }, item);
    }
}
