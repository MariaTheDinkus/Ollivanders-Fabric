package com.zundrel.ollivanders.api.cores;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.SystemUtil;

public class Core implements ICore {
    private final String name;
    private final int rarity;
    private final ItemGroup group;

    public Core(Item coreItem, String name, int rarity) {
        this.name = name;
        this.rarity = rarity;
        this.group = FabricItemGroupBuilder.build(new Identifier(OllivandersAPI.MODID, name + "_wands"), () -> new ItemStack(coreItem));

        CoreRegistry.weightedCollection.add(rarity, name);
    }

    public Core(Item coreItem, String name) {
        this.name = name;
        this.rarity = 0;
        this.group = FabricItemGroupBuilder.build(new Identifier(OllivandersAPI.MODID, name + "_wands"), () -> new ItemStack(coreItem));
    }

    public Core(String name, int rarity) {
        this.name = name;
        this.rarity = rarity;
        this.group = null;

        CoreRegistry.weightedCollection.add(rarity, name);
    }

    public Core(String name) {
        this.name = name;
        this.rarity = 0;
        this.group = null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getTranslationKey() {
        return SystemUtil.createTranslationKey("core", CoreRegistry.CORE.getId(this));
    }

    @Override
    public int getRarity() {
        return rarity;
    }

    @Override
    public ItemGroup getGroup() {
        return group;
    }
}
