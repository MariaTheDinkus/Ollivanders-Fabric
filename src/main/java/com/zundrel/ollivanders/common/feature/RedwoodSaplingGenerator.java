package com.zundrel.ollivanders.common.feature;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;

public class RedwoodSaplingGenerator extends LargeTreeSaplingGenerator {
    @Override
    protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
        return new RedwoodTreeFeature(DefaultFeatureConfig::deserialize, true);
    }

    @Override
    protected AbstractTreeFeature<DefaultFeatureConfig> createLargeTreeFeature(Random random) {
        return new MegaRedwoodTreeFeature(DefaultFeatureConfig::deserialize, false, random.nextBoolean());
    }
}