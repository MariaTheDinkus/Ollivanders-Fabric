package com.zundrel.ollivanders.api.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface ISelfSpell extends ISpell {
    void onSelf(int powerLevel, World world, PlayerEntity player);
}
