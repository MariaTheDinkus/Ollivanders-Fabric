package com.zundrel.ollivanders.api.registries;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.utils.WeightedCollection;
import com.zundrel.ollivanders.api.woods.IWood;
import com.zundrel.ollivanders.api.woods.Wood;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

import java.util.Random;
import java.util.UUID;

public class WoodRegistry {
    public static DefaultedRegistry<Wood> WOOD = register("wood", new DefaultedRegistry<>("oak"));
    public static WeightedCollection<String> weightedCollection = new WeightedCollection<>();

    private static <T, R extends MutableRegistry<T>> R register(String string_1, R mutableRegistry_1) {
        Identifier identifier_1 = new Identifier(OllivandersAPI.MODID, string_1);
        return Registry.REGISTRIES.add(identifier_1, mutableRegistry_1);
    }

    public static IWood findWood(String name) {
        return WOOD.stream().filter(it -> it.getName().equals(name)).findAny().orElse(null);
    }

    public static IWood findDestinedWood(UUID uuid) {
        Random random = new Random();
        random.setSeed(uuid.hashCode());

        return findWood(weightedCollection.next(random));
    }
}
