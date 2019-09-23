package com.zundrel.ollivanders.api.woods;

import com.zundrel.ollivanders.api.registries.WoodRegistry;
import net.minecraft.util.SystemUtil;

public class Wood implements IWood {
    private final String name;
    private final int color;
    private final int rarity;

    public Wood(String name, int color, int rarity) {
        this.name = name;
        this.color = color;
        this.rarity = rarity;
        WoodRegistry.weightedCollection.add(rarity, name);
    }

    public Wood(String name, int color) {
        this.name = name;
        this.color = color;
        this.rarity = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTranslationKey() {
        return SystemUtil.createTranslationKey("wood", WoodRegistry.WOOD.getId(this));
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getRarity() {
        return rarity;
    }
}
