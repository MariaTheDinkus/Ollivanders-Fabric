package com.zundrel.ollivanders.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public interface IProjectileSpell extends ISpell {
    boolean shouldCollideFluid();

    void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult);

    void onHitEntity(int powerLevel, World world, PlayerEntity player, EntityHitResult rayTraceResult);
}
