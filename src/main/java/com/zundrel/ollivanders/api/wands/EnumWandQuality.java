package com.zundrel.ollivanders.api.wands;

import com.zundrel.ollivanders.Ollivanders;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.SystemUtil;

public enum EnumWandQuality implements StringIdentifiable {
    TERRIBLE("terrible"),
    POOR("poor"),
    AVERAGE("average"),
    EXCEPTIONAL("exceptional"),
    PERFECT("perfect");

    String name;

    EnumWandQuality(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    public static EnumWandQuality getFromName(String name) {
        for (EnumWandQuality enumWandQuality : values()) {
            if (name == enumWandQuality.asString()) {
                return enumWandQuality;
            }
        }
        return TERRIBLE;
    }

    public String getTranslationKey() {
        return SystemUtil.createTranslationKey("quality", new Identifier(Ollivanders.MODID, name));
    }
}
