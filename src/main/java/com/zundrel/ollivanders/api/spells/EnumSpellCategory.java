package com.zundrel.ollivanders.api.spells;

import net.minecraft.util.StringIdentifiable;

public enum EnumSpellCategory implements StringIdentifiable {
    CHARM("charm"),
    COUNTER("counter"),
    CURSE("curse"),
    FLOO("floo"),
    HEALING("healing"),
    HEX("hex"),
    JINX("jinx"),
    NONE("none"),
    TRANSFIGURATION("transfiguration");

    String name;

    EnumSpellCategory(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
