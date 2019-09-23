package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.registries.WoodRegistry;
import com.zundrel.ollivanders.api.woods.Wood;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModWoods {
    public static Wood ACACIA;
    public static Wood BIRCH;
    public static Wood DARK_OAK;
    public static Wood ELDER;
    public static Wood JUNGLE;
    public static Wood OAK;
    public static Wood REDWOOD;
    public static Wood SPRUCE;
    public static Wood VINE;
    public static Wood LAUREL;

    public static void init() {
        ACACIA = register("acacia", new Wood("acacia", 0xB75E12, 10));
        BIRCH = register("birch", new Wood("birch", 0xFFEBD0, 20));
        DARK_OAK = register("dark_oak", new Wood("dark_oak", 0x351A11, 20));
        ELDER = register("elder", new Wood("elder", 0x8E6053));
        JUNGLE = register("jungle", new Wood("jungle", 0xAC7E5D, 10));
        OAK = register("oak", new Wood("oak", 0x906B41, 20));
        REDWOOD = register("redwood", new Wood("redwood", 0x562424, 15));
        SPRUCE = register("spruce", new Wood("spruce", 0x836044, 15));
        VINE = register("vine", new Wood("vine", 0x66584C, 10));
        LAUREL = register("laurel", new Wood("laurel", 0xC59969, 10));
    }

    public static <T extends Wood> T register(String name, T wood) {
        return Registry.register(WoodRegistry.WOOD, new Identifier(Ollivanders.MODID, name), wood);
    }
}
