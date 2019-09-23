package com.zundrel.ollivanders.client.events;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import com.zundrel.ollivanders.client.keybindings.ModKeybinds;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.util.PacketByteBuf;

public class ClientEventHandler {
    public static float overlayTime = 0;
    public static int cooldown = 0;

    public static void init() {
        ClientTickCallback.EVENT.register(e -> {
            if (cooldown <= 0) {
                if (ModKeybinds.DECREASE_POWER_LEVEL.isPressed()) {
                    IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(e.player);

                    if (e.player.isSneaking())
                        playerComponent.setPowerLevel(1);
                    else
                        playerComponent.subPowerLevel(1);

                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeUuid(e.player.getUuid());
                    passedData.writeInt(playerComponent.getPowerLevel());

                    ClientSidePacketRegistry.INSTANCE.sendToServer(Ollivanders.POWER_LEVEL_PACKET, passedData);
                    overlayTime = 90;
                    cooldown = 4;
                } else if (ModKeybinds.INCREASE_POWER_LEVEL.isPressed()) {
                    IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(e.player);

                    if (e.player.isSneaking())
                        playerComponent.setPowerLevel(5);
                    else
                        playerComponent.addPowerLevel(1);

                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeUuid(e.player.getUuid());
                    passedData.writeInt(playerComponent.getPowerLevel());

                    ClientSidePacketRegistry.INSTANCE.sendToServer(Ollivanders.POWER_LEVEL_PACKET, passedData);
                    overlayTime = 90;
                    cooldown = 4;
                }
            }

            if (cooldown > 0)
                cooldown--;
        });
    }
}
