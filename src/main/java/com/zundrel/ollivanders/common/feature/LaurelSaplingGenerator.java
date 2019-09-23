package com.zundrel.ollivanders.common.feature;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;

public class LaurelSaplingGenerator extends SaplingGenerator {
    @Override
    protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random random) {
        return (random.nextInt(10) == 0 ? new LargeLaurelTreeFeature(DefaultFeatureConfig::deserialize, true) : new LaurelTreeFeature(DefaultFeatureConfig::deserialize, true));
    }
}