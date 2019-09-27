package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.component.IWorldComponent;
import com.zundrel.ollivanders.api.spells.*;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import com.zundrel.ollivanders.common.spells.world.FlooWorldSpell;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class SpellHandler {
    public static void cast(ISpell spell, int powerLevel, World world, PlayerEntity player) {
        cast(spell, powerLevel, world, player, -1, -1);
    }

    public static void cast(ISpell spell, int powerLevel, World world, PlayerEntity player, int radius, int duration) {
        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);
        int usedPowerLevel = Math.min(powerLevel, Math.min(spell.getMaxSpellPower(player), playerComponent.getWandMaxPowerLevel()));

        player.getEntityWorld().playSound(null, player.x, player.y, player.z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1.5F);

        if (!world.isClient()) {
            if (spell instanceof ISelfSpell) {
                ISelfSpell selfSpell = (ISelfSpell) spell;
                selfSpell.onSelf(usedPowerLevel, world, player);
                selfSpell.advanceSpellLevel(player);
            } else if (spell instanceof IRaytraceSpell) {
                IRaytraceSpell raytraceSpell = (IRaytraceSpell) spell;
                raytraceSpell.onHitBlock(usedPowerLevel, world, player, (BlockHitResult) raycast(player, 160));
                raytraceSpell.advanceSpellLevel(player);
            } else if (spell instanceof IProjectileSpell) {
                IProjectileSpell projectileSpell = (IProjectileSpell) spell;
                EntitySpellProjectile projectile = new EntitySpellProjectile(world, powerLevel, projectileSpell, player.x, player.y + player.getActiveEyeHeight(player.getPose(), player.getDimensions(player.getPose())), player.z, player, projectileSpell.shouldCollideFluid());
                projectile.setProperties(player, player.pitch, player.yaw, 0, 1.25F, 0F);
                world.spawnEntity(projectile);
            } else if (spell instanceof IStationarySpell) {
                IStationarySpell stationarySpell = (IStationarySpell) spell;
                BlockHitResult blockHitResult = (BlockHitResult) raycast(player, 160);
                BlockPos pos = stationarySpell.isOffset() ? blockHitResult.getBlockPos().offset(blockHitResult.getSide()) : blockHitResult.getBlockPos();

                IWorldComponent worldComponent = OllivandersComponents.getWorldComponent(world);
                if (stationarySpell.getCategory() != EnumSpellCategory.FLOO && worldComponent.getStationarySpell(pos) == null) {
                    if (!world.isAir(pos)) {
                        worldComponent.addStationarySpell(new WorldSpell(stationarySpell, pos, stationarySpell.getRadius(), stationarySpell.getDuration()));
                        playerComponent.addSpellLevel(stationarySpell.getName(), 1);
                    }
                } else if (worldComponent.getFlooSpell(pos) == null && world.getBlockState(pos).getBlock() instanceof FireBlock && world.getBlockEntity(pos.up()) instanceof SignBlockEntity) {
                    SignBlockEntity signBlockEntity = (SignBlockEntity) world.getBlockEntity(pos.up());

                    if (signBlockEntity != null && signBlockEntity.text[0] != null) {
                        Ollivanders.LOGGER.info(world.getBlockState(pos.up()).get(Properties.HORIZONTAL_FACING));
                        Ollivanders.LOGGER.info(world.getBlockState(pos.up()).get(Properties.HORIZONTAL_FACING).getName());
                        worldComponent.putFlooSpell(signBlockEntity.text[0].asString().replace(" ", "_"), new FlooWorldSpell(signBlockEntity.text[0].asString().replace(" ", "_"), signBlockEntity.text[0].asString(), stationarySpell, pos, world.getBlockState(pos.up()).get(Properties.HORIZONTAL_FACING).getName(), stationarySpell.getRadius()));
                        playerComponent.addSpellLevel(stationarySpell.getName(), 1);
                    }
                }
            }
        }
    }

    private static HitResult raycast(Entity entity, double len) {
        Vec3d vec = new Vec3d(entity.x, entity.y, entity.z);
        if (entity instanceof PlayerEntity)
            vec = vec.add(new Vec3d(0, ((PlayerEntity) entity).getActiveEyeHeight(entity.getPose(), entity.getDimensions(entity.getPose())), 0));

        Vec3d look = entity.getRotationVector();
        if (look == null)
            return null;

        return raycast(entity, vec, look, len);
    }

    private static HitResult raycast(Entity entity, Vec3d origin, Vec3d ray, double len) {
        Vec3d next = origin.add(ray.normalize().multiply(len));
        HitResult pos = entity.getEntityWorld().rayTrace(new RayTraceContext(origin, next, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, entity));
        return pos;
    }
}
