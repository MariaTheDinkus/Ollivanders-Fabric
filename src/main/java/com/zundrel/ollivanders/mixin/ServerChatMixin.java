package com.zundrel.ollivanders.mixin;

import com.zundrel.ollivanders.api.event.ServerChatCallback;
import com.zundrel.ollivanders.api.utils.WandUtils;
import net.minecraft.SharedConstants;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerChatMixin implements ServerPlayPacketListener {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    private int messageCooldown;

    @Shadow
    public void disconnect(Text text_1) {}

    @Shadow
    public void sendPacket(Packet<?> packet_1) {}

    @Shadow
    private void executeCommand(String string_1) {}

    /**
     * Added a proper chat event into onChatMessage.
     * @author Zundrel
     */
    @Overwrite
    public void onChatMessage(ChatMessageC2SPacket chatMessageC2SPacket_1) {
        NetworkThreadUtils.forceMainThread(chatMessageC2SPacket_1, this, this.player.getServerWorld());
        if (this.player.getClientChatVisibility() == ChatVisibility.HIDDEN) {
            this.sendPacket(new ChatMessageS2CPacket((new TranslatableText("chat.cannotSend", new Object[0])).formatted(Formatting.RED)));
        } else {
            this.player.updateLastActionTime();
            String string_1 = chatMessageC2SPacket_1.getChatMessage();
            string_1 = StringUtils.normalizeSpace(string_1);
            int radius = ServerChatCallback.EVENT.invoker().interact(player, string_1);

            for(int int_1 = 0; int_1 < string_1.length(); ++int_1) {
                if (!SharedConstants.isValidChar(string_1.charAt(int_1))) {
                    this.disconnect(new TranslatableText("multiplayer.disconnect.illegal_characters", new Object[0]));
                    return;
                }
            }

            if (string_1.startsWith("/")) {
                this.executeCommand(string_1);
            } else {
                Text text_1 = new TranslatableText("chat.type.text", new Object[]{this.player.getDisplayName(), string_1});

                World world = player.getEntityWorld();
                ArrayList<PlayerEntity> playerEntities = (ArrayList<PlayerEntity>) world.getPlayers();

                if (radius >= 0) {
                    for (PlayerEntity otherPlayer : playerEntities) {
                        if (WandUtils.compareCoordinatesDistance(player.getBlockPos(), otherPlayer.getBlockPos()) < 20) {
                            this.server.getPlayerManager().broadcastChatMessage(text_1, false);
                        }
                    }
                } else {
                    this.server.getPlayerManager().broadcastChatMessage(text_1, false);
                }
            }

            this.messageCooldown += 20;
            if (this.messageCooldown > 200 && !this.server.getPlayerManager().isOperator(this.player.getGameProfile())) {
                this.disconnect(new TranslatableText("disconnect.spam", new Object[0]));
            }

        }
    }
}
