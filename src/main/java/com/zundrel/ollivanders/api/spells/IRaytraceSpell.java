package com.zundrel.ollivanders.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public interface IRaytraceSpell extends ISpell {
    void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult);
}
