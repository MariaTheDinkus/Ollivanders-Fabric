package com.zundrel.ollivanders.mixin;

import com.zundrel.ollivanders.api.wands.IWandItem;
import com.zundrel.ollivanders.client.events.ClientEventHandler;
import com.zundrel.ollivanders.client.gui.PowerBarScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinPowerBar {
    @Inject(method = "render", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(F)V")), at = @At(value = "INVOKE", ordinal = 0))
    private void renderOverlay(float var1, long nanoTime, boolean var4, CallbackInfo callbackInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;
        PowerBarScreen powerBarScreen = new PowerBarScreen(mc);

        if (ClientEventHandler.overlayTime > 0)
            ClientEventHandler.overlayTime -= var1;

        if (ClientEventHandler.overlayTime > 0 || !player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() instanceof IWandItem)
            powerBarScreen.renderPowerBar(mc.window.getScaledWidth(), mc.window.getScaledHeight());
    }
}
