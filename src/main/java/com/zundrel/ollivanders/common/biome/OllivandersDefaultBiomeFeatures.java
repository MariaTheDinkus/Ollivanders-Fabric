package com.zundrel.ollivanders.common.biome;

import com.zundrel.ollivanders.common.feature.LaurelTreeFeature;
import com.zundrel.ollivanders.common.feature.MegaRedwoodTreeFeature;
import com.zundrel.ollivanders.common.feature.RedwoodTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.RandomBooleanFeatureConfig;

public class OllivandersDefaultBiomeFeatures {

	public static void addRedwoodForestTrees(Biome biome) {
	    RedwoodTreeFeature tallRedwoodTreeFeature = new RedwoodTreeFeature(DefaultFeatureConfig::deserialize, false);
	    MegaRedwoodTreeFeature megaRedwoodTree = new MegaRedwoodTreeFeature(DefaultFeatureConfig::deserialize, false, true);

        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfig(tallRedwoodTreeFeature, FeatureConfig.DEFAULT, megaRedwoodTree, FeatureConfig.DEFAULT), Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(3, 0.2F, 1)));
	}

	public static void addLaurelForestTrees(Biome biome) {
        LaurelTreeFeature laurelTreeFeature = new LaurelTreeFeature(DefaultFeatureConfig::deserialize, false);

        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(laurelTreeFeature, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(3, 0.2F, 1)));
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.NORMAL_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(3, 0.2F, 1)));
        biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Biome.configureFeature(Feature.DARK_OAK_TREE, FeatureConfig.DEFAULT, Decorator.COUNT_EXTRA_HEIGHTMAP, new CountExtraChanceDecoratorConfig(3, 0.2F, 1)));
    }

}