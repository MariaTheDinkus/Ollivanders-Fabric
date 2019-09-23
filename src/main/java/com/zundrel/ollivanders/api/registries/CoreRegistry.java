package com.zundrel.ollivanders.api.registries;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.cores.Core;
import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.utils.WeightedCollection;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class CoreRegistry {
    public static DefaultedRegistry<Core> CORE = register("core", new DefaultedRegistry<>("dragon_heartstring"));
    public static WeightedCollection<String> weightedCollection = new WeightedCollection<>();

    private static <T, R extends MutableRegistry<T>> R register(String string_1, R mutableRegistry_1) {
        Identifier identifier_1 = new Identifier(OllivandersAPI.MODID, string_1);
        return Registry.REGISTRIES.add(identifier_1, mutableRegistry_1);
    }

    public static Set<Identifier> getKeys() {
        return CORE.getIds();
    }

    public static ICore findCore(String name) {
        return CORE.stream().filter(it -> it.getName().equals(name)).findAny().orElse(null);
    }

    public static ICore findDestinedCore(UUID uuid) {
        Random random = new Random();
        random.setSeed(uuid.hashCode());

        return findCore(weightedCollection.next(random));
    }
}
