package com.zundrel.ollivanders.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Unique
    private EntitySpawnS2CPacket packet;

    public MixinClientPlayNetworkHandler() {
    }

    @Inject(
        method = {"onEntitySpawn"},
        at = {@At("HEAD")}
    )
    public void onEntitySpawnPacket(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        this.packet = packet;
    }

    @ModifyVariable(
        method = {"onEntitySpawn"},
        at = @At(value = "STORE"),
        ordinal = 0
    )
    public Entity onEntitySpawn(Entity oldObject) {
        Entity entity = oldObject;
        if (oldObject == null) {
            entity = packet.getEntityTypeId().create(((ClientPlayNetworkHandler) (Object) this).getWorld());
            entity.setPosition(packet.getX(), packet.getY(), packet.getZ());
        }
        return entity;
    }
}
