package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.cores.Core;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModCores {
    public static Core DRAGON_HEARTSTRING;
    public static Core PHOENIX_FEATHER;
    public static Core UNICORN_TAIL_HAIR;
    public static Core THESTRAL_TAIL_HAIR;

    public static void init() {
        DRAGON_HEARTSTRING = register("dragon_heartstring", new Core(ModItems.DRAGON_HEARTSTRING, "dragon_heartstring",80));
        PHOENIX_FEATHER = register("phoenix_feather", new Core(ModItems.PHOENIX_FEATHER, "phoenix_feather", 4));
        UNICORN_TAIL_HAIR = register("unicorn_tail_hair", new Core(ModItems.UNICORN_TAIL_HAIR, "unicorn_tail_hair", 80));
        THESTRAL_TAIL_HAIR = register("thestral_tail_hair", new Core("thestral_tail_hair"));
    }

    public static <T extends Core> T register(String name, T core) {
        return Registry.register(CoreRegistry.CORE, new Identifier(Ollivanders.MODID, name), core);
    }
}
