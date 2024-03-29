package com.zundrel.ollivanders;

import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.spells.ISpell;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import com.zundrel.ollivanders.common.events.CommonEventHandler;
import com.zundrel.ollivanders.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Ollivanders implements ModInitializer
{
    public static final String MODID = "ollivanders";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static ItemGroup generalItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "general"), () -> new ItemStack(ModItems.PHOENIX_FEATHER));

    public static final Identifier SPELL_PROJECTILE_PACKET = new Identifier(MODID, "spell_projectile_packet");
    public static final Identifier SPELL_PACKET = new Identifier(MODID, "spell_packet");
    public static final Identifier POWER_LEVEL_PACKET = new Identifier(MODID, "power_level_packet");

    @Override
    public void onInitialize() {
        CommonEventHandler.init();

        ModComponents.init();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModBiomes.init();
        ModCores.init();
        ModWoods.init();
        ModSpells.init();
        ModCommands.init();

        ClientSidePacketRegistry.INSTANCE.register(SPELL_PROJECTILE_PACKET, ((packetContext, packetByteBuf) -> {
            double x = packetByteBuf.readDouble();
            double y = packetByteBuf.readDouble();
            double z = packetByteBuf.readDouble();
            float pitch = packetByteBuf.readFloat();
            float yaw = packetByteBuf.readFloat();
            int entityId = packetByteBuf.readInt();
            UUID uuid = packetByteBuf.readUuid();

            packetContext.getTaskQueue().execute(() -> {
                Entity entity = new EntitySpellProjectile(packetContext.getPlayer().getEntityWorld(), x, y, z);

                entity.updateTrackedPosition(x, y, z);
                entity.pitch = (pitch * 360) / 256.0F;
                entity.yaw = (yaw * 360) / 256.0F;
                entity.setEntityId(entityId);
                entity.setUuid(uuid);
                ((ClientWorld) packetContext.getPlayer().getEntityWorld()).addEntity(entityId, entity);
            });
        }));

        ClientSidePacketRegistry.INSTANCE.register(SPELL_PACKET, ((packetContext, packetByteBuf) -> {
            ISpell spell = SpellRegistry.findSpell(packetByteBuf.readString());

            packetContext.getTaskQueue().execute(() -> {
                IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(packetContext.getPlayer());

                playerComponent.setSpell(spell);
            });
        }));

        ServerSidePacketRegistry.INSTANCE.register(POWER_LEVEL_PACKET, ((packetContext, packetByteBuf) -> {
            UUID uuid = packetByteBuf.readUuid();
            int powerLevel = packetByteBuf.readInt();

            packetContext.getTaskQueue().execute(() -> {
                if (packetContext.getPlayer().getUuid().equals(uuid)) {
                    IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(packetContext.getPlayer());

                    playerComponent.setPowerLevel(powerLevel);
                }
            });
        }));
    }
}
