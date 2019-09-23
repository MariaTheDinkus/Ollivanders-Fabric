package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.common.biome.OakenForestBiome;
import com.zundrel.ollivanders.common.biome.RedwoodForestBiome;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static final Biome OAKEN_FOREST = Registry.register(Registry.BIOME, new Identifier(Ollivanders.MODID, "oaken_forest"), new OakenForestBiome());
    public static final Biome REDWOOD_FOREST = Registry.register(Registry.BIOME, new Identifier(Ollivanders.MODID, "redwood_forest"), new RedwoodForestBiome());

    public static void init() {
        OverworldBiomes.addContinentalBiome(OAKEN_FOREST, OverworldClimate.TEMPERATE, 2D);
        OverworldBiomes.addContinentalBiome(REDWOOD_FOREST, OverworldClimate.TEMPERATE, 2D);

        FabricBiomes.addSpawnBiome(OAKEN_FOREST);
        FabricBiomes.addSpawnBiome(REDWOOD_FOREST);
    }
}
