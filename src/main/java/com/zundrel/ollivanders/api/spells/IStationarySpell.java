package com.zundrel.ollivanders.api.spells;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStationarySpell extends ISpell {
    boolean isOffset();

    boolean isInfinite();

    int getRadius();

    int getDuration();

    void onStationaryTick(World world, WorldSpell stationarySpellObject, BlockPos pos);
}
