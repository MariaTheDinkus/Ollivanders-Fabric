package com.zundrel.ollivanders.common.entity;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.spells.IProjectileSpell;
import com.zundrel.ollivanders.api.spells.ISpell;
import com.zundrel.ollivanders.common.registry.ModEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.thrown.ThrownEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class EntitySpellProjectile extends ThrownEntity
{
    protected boolean stationary = false;
	private DefaultParticleType impactParticle = ParticleTypes.END_ROD;

	private float impactParticleSpeed = 2;

    private int power = 0;

    private int powerLevel;
    private IProjectileSpell spell;

    private PlayerEntity entity = null;

    private boolean collideFluids = false;

    public EntitySpellProjectile(World worldIn, int powerLevel, IProjectileSpell spell, double x, double y, double z, PlayerEntity player, boolean collideFluids) {
        super(ModEntities.SPELL_PROJECTILE, worldIn);

        this.powerLevel = powerLevel;
        this.spell = spell;
        this.entity = player;
        this.collideFluids = collideFluids;

        this.setPosition(x, y, z);
    }

    public EntitySpellProjectile(World world, double x, double y, double z) {
        super(ModEntities.SPELL_PROJECTILE, world);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public EntitySpellProjectile(World world) {
        super(ModEntities.SPELL_PROJECTILE, world);
    }

    public EntitySpellProjectile(EntityType<? extends ThrownEntity> entityType, World world, boolean collideFluids) {
        super(ModEntities.SPELL_PROJECTILE, world);

        this.collideFluids = collideFluids;
    }

    public void sync() {
        Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(getEntityWorld(), getBlockPos());

        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeDouble(x);
        passedData.writeDouble(y);
        passedData.writeDouble(z);
        passedData.writeFloat(pitch);
        passedData.writeFloat(yaw);
        passedData.writeInt(getEntityId());
        passedData.writeUuid(getUuid());

        watchingPlayers.forEach(playerEntity -> {
            ServerSidePacketRegistry.INSTANCE.sendToPlayer(playerEntity, Ollivanders.SPELL_PROJECTILE_PACKET, passedData);
        });
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
	public void tick()
	{
	    if (age > 200) {
	        remove();
        }

        world.addParticle(impactParticle, x, y, z, 0 , 0, 0);

	    if (collideFluids && !world.getFluidState(getBlockPos()).isEmpty())
	        onCollision(new BlockHitResult(new Vec3d(x, y, z), Direction.UP, getBlockPos(), true));

		super.tick();
	}

	@Override
	protected void onCollision(HitResult result)
	{
	    if (result.getType() == HitResult.Type.BLOCK) {
            for (int i = 0; i < 16; ++i) {
                world.addParticle(impactParticle, x, y, z,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8);
            }

            if (!world.isClient()) {
                spell.onHitBlock(powerLevel, world, entity, (BlockHitResult) result);
                spell.advanceSpellLevel(entity);
                remove();
            }
        } else if (result.getType() == HitResult.Type.ENTITY && ((EntityHitResult) result).getEntity() != entity) {
            for (int i = 0; i < 16; ++i) {
                world.addParticle(impactParticle, x, y, z,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8,
                        (random.nextDouble() * impactParticleSpeed * 2 - impactParticleSpeed) / 8);
            }

            if (!world.isClient()) {
                spell.onHitEntity(powerLevel, world, entity, (EntityHitResult) result);
                spell.advanceSpellLevel(entity);
                remove();
            }
        }
	}

    public ISpell getSpell() {
        return spell;
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    protected void setImpactParticleSpeed(float projectileSpeed)
	{
		impactParticleSpeed = projectileSpeed;
	}

	protected void setStationary(boolean value)
	{
		stationary = value;
	}
}