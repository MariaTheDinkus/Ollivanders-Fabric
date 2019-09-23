package com.zundrel.ollivanders.api.registries;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.spells.ISpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

public class SpellRegistry {
    public static DefaultedRegistry<Spell> SPELL = register("spell", new DefaultedRegistry<>("accio"));

    private static <T, R extends MutableRegistry<T>> R register(String string_1, R mutableRegistry_1) {
        Identifier identifier_1 = new Identifier(OllivandersAPI.MODID, string_1);
        return Registry.REGISTRIES.add(identifier_1, mutableRegistry_1);
    }

    public static ISpell findSpell(String name) {
        return SPELL.stream().filter(it -> it.getName().equals(name)).findAny().orElse(null);
    }

    public static ISpell findSpell(Spell spell) {
        return SPELL.stream().filter(it -> it.getName().equals(spell.getName())).findAny().orElse(null);
    }

    public static boolean isSpellVerbal(String verbalName) {
        return findSpellVerbal(verbalName) != null;
    }

    public static ISpell findSpellVerbal(String verbalName) {
        return SPELL.stream().filter(it -> it.getVerbalName().equals(verbalName)).findAny().orElse(null);
    }
}
