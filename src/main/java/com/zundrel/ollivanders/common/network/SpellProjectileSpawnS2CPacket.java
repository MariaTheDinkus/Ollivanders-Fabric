package com.zundrel.ollivanders.common.network;

import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.UUID;

public class SpellProjectileSpawnS2CPacket implements Packet<ClientPlayPacketListener> {
    private int id;
    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private int velocityX;
    private int velocityY;
    private int velocityZ;
    private int pitch;
    private int yaw;
    private EntityType<?> entityTypeId;
    private int entityData;

    public SpellProjectileSpawnS2CPacket() {
    }

    public SpellProjectileSpawnS2CPacket(int int_1, UUID uUID_1, double double_1, double double_2, double double_3, float float_1, float float_2, EntityType<?> entityType_1, int int_2, Vec3d vec3d_1) {
        this.id = int_1;
        this.uuid = uUID_1;
        this.x = double_1;
        this.y = double_2;
        this.z = double_3;
        this.pitch = MathHelper.floor(float_1 * 256.0F / 360.0F);
        this.yaw = MathHelper.floor(float_2 * 256.0F / 360.0F);
        this.entityTypeId = entityType_1;
        this.entityData = int_2;
        this.velocityX = (int)(MathHelper.clamp(vec3d_1.x, -3.9D, 3.9D) * 8000.0D);
        this.velocityY = (int)(MathHelper.clamp(vec3d_1.y, -3.9D, 3.9D) * 8000.0D);
        this.velocityZ = (int)(MathHelper.clamp(vec3d_1.z, -3.9D, 3.9D) * 8000.0D);
    }

    public SpellProjectileSpawnS2CPacket(Entity entity_1) {
        this(entity_1, 0);
    }

    public SpellProjectileSpawnS2CPacket(Entity entity_1, int int_1) {
        this(entity_1.getEntityId(), entity_1.getUuid(), entity_1.x, entity_1.y, entity_1.z, entity_1.pitch, entity_1.yaw, entity_1.getType(), int_1, entity_1.getVelocity());
    }

    public SpellProjectileSpawnS2CPacket(Entity entity_1, EntityType<?> entityType_1, int int_1, BlockPos blockPos_1) {
        this(entity_1.getEntityId(), entity_1.getUuid(), (double)blockPos_1.getX(), (double)blockPos_1.getY(), (double)blockPos_1.getZ(), entity_1.pitch, entity_1.yaw, entityType_1, int_1, entity_1.getVelocity());
    }

    public void read(PacketByteBuf packetByteBuf_1) throws IOException {
        this.id = packetByteBuf_1.readVarInt();
        this.uuid = packetByteBuf_1.readUuid();
        this.entityTypeId = (EntityType)Registry.ENTITY_TYPE.get(packetByteBuf_1.readVarInt());
        this.x = packetByteBuf_1.readDouble();
        this.y = packetByteBuf_1.readDouble();
        this.z = packetByteBuf_1.readDouble();
        this.pitch = packetByteBuf_1.readByte();
        this.yaw = packetByteBuf_1.readByte();
        this.entityData = packetByteBuf_1.readInt();
        this.velocityX = packetByteBuf_1.readShort();
        this.velocityY = packetByteBuf_1.readShort();
        this.velocityZ = packetByteBuf_1.readShort();
    }

    public void write(PacketByteBuf packetByteBuf_1) throws IOException {
        packetByteBuf_1.writeVarInt(this.id);
        packetByteBuf_1.writeUuid(this.uuid);
        packetByteBuf_1.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.entityTypeId));
        packetByteBuf_1.writeDouble(this.x);
        packetByteBuf_1.writeDouble(this.y);
        packetByteBuf_1.writeDouble(this.z);
        packetByteBuf_1.writeByte(this.pitch);
        packetByteBuf_1.writeByte(this.yaw);
        packetByteBuf_1.writeInt(this.entityData);
        packetByteBuf_1.writeShort(this.velocityX);
        packetByteBuf_1.writeShort(this.velocityY);
        packetByteBuf_1.writeShort(this.velocityZ);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        MinecraftClient client = MinecraftClient.getInstance();
        NetworkThreadUtils.forceMainThread(this, clientPlayPacketListener, client);
        double x = getX();
        double y = getY();
        double z = getZ();
        Entity entity = new EntitySpellProjectile(client.world, x, y, z);

        if (entity != null) {
            int int_1 = getId();
            entity.updateTrackedPosition(x, y, z);
            entity.pitch = (float)(getPitch() * 360) / 256.0F;
            entity.yaw = (float)(getYaw() * 360) / 256.0F;
            entity.setEntityId(int_1);
            entity.setUuid(getUuid());
            client.world.addEntity(int_1, entity);
        }
    }

    @Environment(EnvType.CLIENT)
    public int getId() {
        return this.id;
    }

    @Environment(EnvType.CLIENT)
    public UUID getUuid() {
        return this.uuid;
    }

    @Environment(EnvType.CLIENT)
    public double getX() {
        return this.x;
    }

    @Environment(EnvType.CLIENT)
    public double getY() {
        return this.y;
    }

    @Environment(EnvType.CLIENT)
    public double getZ() {
        return this.z;
    }

    @Environment(EnvType.CLIENT)
    public double getVelocityX() {
        return (double)this.velocityX / 8000.0D;
    }

    @Environment(EnvType.CLIENT)
    public double getVelocityY() {
        return (double)this.velocityY / 8000.0D;
    }

    @Environment(EnvType.CLIENT)
    public double getVelocityz() {
        return (double)this.velocityZ / 8000.0D;
    }

    @Environment(EnvType.CLIENT)
    public int getPitch() {
        return this.pitch;
    }

    @Environment(EnvType.CLIENT)
    public int getYaw() {
        return this.yaw;
    }

    @Environment(EnvType.CLIENT)
    public EntityType<?> getEntityTypeId() {
        return this.entityTypeId;
    }

    @Environment(EnvType.CLIENT)
    public int getEntityData() {
        return this.entityData;
    }
}
