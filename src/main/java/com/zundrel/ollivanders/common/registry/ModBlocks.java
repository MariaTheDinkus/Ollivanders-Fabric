package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.common.blocks.*;
import com.zundrel.ollivanders.common.feature.LaurelSaplingGenerator;
import com.zundrel.ollivanders.common.feature.RedwoodSaplingGenerator;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static CustomFireBlock GREEN_FIRE;

    public static CustomSaplingBlock LAUREL_SAPLING;
    public static LogBlock LAUREL_LOG;
    public static LeavesBlock LAUREL_LEAVES;
    public static LogBlock STRIPPED_LAUREL_LOG;
    public static PillarBlock LAUREL_WOOD;
    public static PillarBlock STRIPPED_LAUREL_WOOD;
    public static Block LAUREL_PLANKS;
    public static CustomStairsBlock LAUREL_STAIRS;
    public static SlabBlock LAUREL_SLAB;
    public static FenceBlock LAUREL_FENCE;
    public static FenceGateBlock LAUREL_FENCE_GATE;
    public static CustomDoorBlock LAUREL_DOOR;
    public static CustomTrapdoorBlock LAUREL_TRAPDOOR;
    public static CustomWoodButtonBlock LAUREL_BUTTON;
    public static CustomPressurePlateBlock LAUREL_PRESSURE_PLATE;

    public static CustomSaplingBlock REDWOOD_SAPLING;
    public static LogBlock REDWOOD_LOG;
    public static LeavesBlock REDWOOD_LEAVES;
    public static LogBlock STRIPPED_REDWOOD_LOG;
    public static PillarBlock REDWOOD_WOOD;
    public static PillarBlock STRIPPED_REDWOOD_WOOD;
    public static Block REDWOOD_PLANKS;
    public static CustomStairsBlock REDWOOD_STAIRS;
    public static SlabBlock REDWOOD_SLAB;
    public static FenceBlock REDWOOD_FENCE;
    public static FenceGateBlock REDWOOD_FENCE_GATE;
    public static CustomDoorBlock REDWOOD_DOOR;
    public static CustomTrapdoorBlock REDWOOD_TRAPDOOR;
    public static CustomWoodButtonBlock REDWOOD_BUTTON;
    public static CustomPressurePlateBlock REDWOOD_PRESSURE_PLATE;

    public static void init() {
        GREEN_FIRE = register("green_fire", new CustomFireBlock(Block.Settings.copy(Blocks.FIRE)));

        LAUREL_SAPLING = register("laurel_sapling", new CustomSaplingBlock(new LaurelSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING).build()));
        LAUREL_LOG = register("laurel_log", new LogBlock(MaterialColor.WOOD, Block.Settings.copy(Blocks.OAK_LEAVES)));
        LAUREL_LEAVES = register("laurel_leaves", new LeavesBlock(Block.Settings.copy(Blocks.OAK_LEAVES)));
        STRIPPED_LAUREL_LOG = register("stripped_laurel_log", new LogBlock(MaterialColor.WOOD, Block.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
        LAUREL_WOOD = register("laurel_wood", new PillarBlock(Block.Settings.copy(Blocks.OAK_WOOD)));
        STRIPPED_LAUREL_WOOD = register("stripped_laurel_wood", new PillarBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));
        LAUREL_PLANKS = register("laurel_planks", new Block(Block.Settings.copy(Blocks.OAK_PLANKS)));

        LAUREL_STAIRS = register("laurel_stairs", new CustomStairsBlock(LAUREL_PLANKS.getDefaultState(), Block.Settings.copy(Blocks.OAK_STAIRS)));
        LAUREL_SLAB = register("laurel_slab", new SlabBlock(Block.Settings.copy(Blocks.OAK_SLAB)));
        LAUREL_FENCE = register("laurel_fence", new FenceBlock(Block.Settings.copy(Blocks.OAK_FENCE)));
        LAUREL_FENCE_GATE = register("laurel_fence_gate", new FenceGateBlock(Block.Settings.copy(Blocks.OAK_FENCE_GATE)));

        LAUREL_DOOR = new CustomDoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).build());
        register("laurel_door", LAUREL_DOOR, new TallBlockItem(LAUREL_DOOR, (new Item.Settings()).group(Ollivanders.generalItemGroup)));

        LAUREL_TRAPDOOR = register("laurel_trapdoor", new CustomTrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).build()));
        
        LAUREL_BUTTON = register("laurel_button", new CustomWoodButtonBlock(Block.Settings.copy(Blocks.OAK_BUTTON)));
        LAUREL_PRESSURE_PLATE = register("laurel_pressure_plate", new CustomPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));

        REDWOOD_SAPLING = register("redwood_sapling", new CustomSaplingBlock(new RedwoodSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING).build()));
        REDWOOD_LOG = register("redwood_log", new LogBlock(MaterialColor.WOOD, Block.Settings.copy(Blocks.OAK_LEAVES)));
        REDWOOD_LEAVES = register("redwood_leaves", new LeavesBlock(Block.Settings.copy(Blocks.OAK_LEAVES)));
        STRIPPED_REDWOOD_LOG = register("stripped_redwood_log", new LogBlock(MaterialColor.WOOD, Block.Settings.copy(Blocks.STRIPPED_OAK_LOG)));
        REDWOOD_WOOD = register("redwood_wood", new PillarBlock(Block.Settings.copy(Blocks.OAK_WOOD)));
        STRIPPED_REDWOOD_WOOD = register("stripped_redwood_wood", new PillarBlock(Block.Settings.copy(Blocks.STRIPPED_OAK_WOOD)));
        REDWOOD_PLANKS = register("redwood_planks", new Block(Block.Settings.copy(Blocks.OAK_PLANKS)));
        REDWOOD_STAIRS = register("redwood_stairs", new CustomStairsBlock(REDWOOD_PLANKS.getDefaultState(), Block.Settings.copy(Blocks.OAK_STAIRS)));
        REDWOOD_SLAB = register("redwood_slab", new SlabBlock(Block.Settings.copy(Blocks.OAK_SLAB)));
        REDWOOD_FENCE = register("redwood_fence", new FenceBlock(Block.Settings.copy(Blocks.OAK_FENCE)));
        REDWOOD_FENCE_GATE = register("redwood_fence_gate", new FenceGateBlock(Block.Settings.copy(Blocks.OAK_FENCE_GATE)));

        REDWOOD_DOOR = new CustomDoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).build());
        register("redwood_door", REDWOOD_DOOR, new TallBlockItem(REDWOOD_DOOR, (new Item.Settings()).group(Ollivanders.generalItemGroup)));

        REDWOOD_TRAPDOOR = register("redwood_trapdoor", new CustomTrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).build()));

        REDWOOD_BUTTON = register("redwood_button", new CustomWoodButtonBlock(Block.Settings.copy(Blocks.OAK_BUTTON)));
        REDWOOD_PRESSURE_PLATE = register("redwood_pressure_plate", new CustomPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, Block.Settings.copy(Blocks.OAK_PRESSURE_PLATE)));
    }

    private static BlockItem createBlockItem(Block block) {
        return createBlockItem(block, Ollivanders.generalItemGroup);
    }

    private static BlockItem createBlockItem(Block block, ItemGroup group) {
        return new BlockItem(block, new Item.Settings().group(group));
    }

    private static <T extends Block> T register(String name, T block) {
        Registry.BLOCK.add(new Identifier(Ollivanders.MODID, name), block);
        if (!(block instanceof CustomFireBlock) && !(block instanceof CustomDoorBlock))
            Registry.ITEM.add(Registry.BLOCK.getId(block), createBlockItem(block));

        return block;
    }

    private static <T extends Block> T register(String name, T block, BlockItem blockItem) {
        Registry.register(Registry.BLOCK, new Identifier(Ollivanders.MODID, name), block);
        if (!(block instanceof CustomFireBlock) && !(block instanceof CustomDoorBlock))
            Registry.register(Registry.ITEM, new Identifier(Ollivanders.MODID, name), blockItem);

        return block;
    }
}
