package com.zundrel.ollivanders.api.spells;

import nerdhub.cardinal.components.api.util.NbtSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public interface IWorldSpell extends NbtSerializable {
    String getName();

    IStationarySpell getSpell();

    BlockPos getPosition();

    int getDuration();

    int getRadius();

    BlockState getBlockState(World world);

    void age(World world);

    void age(World world, int i);

    void kill(World world);

    boolean isDead();

    boolean isInside(BlockPos pos);

    List<Entity> getEntities(World world);

    List<LivingEntity> getLivingEntities(World world);

    List<ItemEntity> getItems(World world);

    double[] vecToSpher (Vec3d vec);

    Vec3d spherToVec (double[] spher);

    void flair (World world, double d);
}
