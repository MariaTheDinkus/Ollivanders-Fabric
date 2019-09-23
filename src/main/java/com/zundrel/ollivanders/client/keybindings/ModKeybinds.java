package com.zundrel.ollivanders.client.keybindings;

import com.zundrel.ollivanders.Ollivanders;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeybinds {
    public static FabricKeyBinding DECREASE_POWER_LEVEL = null;
    public static FabricKeyBinding INCREASE_POWER_LEVEL = null;

    public static void init() {
        KeyBindingRegistry.INSTANCE.addCategory("Ollivanders");

        DECREASE_POWER_LEVEL = FabricKeyBinding.Builder.create(
                new Identifier(Ollivanders.MODID, "decrease_power_level"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_MINUS,
                "Ollivanders"
        ).build();

        INCREASE_POWER_LEVEL = FabricKeyBinding.Builder.create(
                new Identifier(Ollivanders.MODID, "increase_power_level"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                "Ollivanders"
        ).build();

        KeyBindingRegistry.INSTANCE.register(DECREASE_POWER_LEVEL);
        KeyBindingRegistry.INSTANCE.register(INCREASE_POWER_LEVEL);
    }
}
