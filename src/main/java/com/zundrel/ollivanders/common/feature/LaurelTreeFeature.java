//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zundrel.ollivanders.common.feature;

import com.mojang.datafixers.Dynamic;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class LaurelTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {
    private static final BlockState LOG;
    private static final BlockState LEAVES;

    public LaurelTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function_1, boolean boolean_1) {
        super(function_1, boolean_1);
    }

    public boolean generate(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BlockPos blockPos_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        int int_1 = random_1.nextInt(4) + 6;
        int int_2 = 1 + random_1.nextInt(2);
        int int_3 = int_1 - int_2;
        int int_4 = 2 + random_1.nextInt(2);
        boolean boolean_1 = true;
        if (blockPos_1.getY() >= 1 && blockPos_1.getY() + int_1 + 1 <= 256) {
            int int_10;
            int int_11;
            int int_8;
            int int_14;
            for(int_10 = blockPos_1.getY(); int_10 <= blockPos_1.getY() + 1 + int_1 && boolean_1; ++int_10) {
                if (int_10 - blockPos_1.getY() < int_2) {
                    int_11 = 0;
                } else {
                    int_11 = int_4;
                }

                Mutable blockPos$Mutable_1 = new Mutable();

                for(int_8 = blockPos_1.getX() - int_11; int_8 <= blockPos_1.getX() + int_11 && boolean_1; ++int_8) {
                    for(int_14 = blockPos_1.getZ() - int_11; int_14 <= blockPos_1.getZ() + int_11 && boolean_1; ++int_14) {
                        if (int_10 >= 0 && int_10 < 256) {
                            blockPos$Mutable_1.set(int_8, int_10, int_14);
                            if (!isAirOrLeaves(modifiableTestableWorld_1, blockPos$Mutable_1)) {
                                boolean_1 = false;
                            }
                        } else {
                            boolean_1 = false;
                        }
                    }
                }
            }

            if (!boolean_1) {
                return false;
            } else if (isDirtOrGrass(modifiableTestableWorld_1, blockPos_1.down()) && blockPos_1.getY() < 256 - int_1 - 1) {
                this.setToDirt(modifiableTestableWorld_1, blockPos_1.down());
                int_10 = random_1.nextInt(2);
                int_11 = 1;
                int int_12 = 0;

                for(int_8 = 0; int_8 <= int_3; ++int_8) {
                    int_14 = blockPos_1.getY() + int_1 - int_8;

                    for(int int_15 = blockPos_1.getX() - int_10; int_15 <= blockPos_1.getX() + int_10; ++int_15) {
                        int int_16 = int_15 - blockPos_1.getX();

                        for(int int_17 = blockPos_1.getZ() - int_10; int_17 <= blockPos_1.getZ() + int_10; ++int_17) {
                            int int_18 = int_17 - blockPos_1.getZ();
                            if (Math.abs(int_16) != int_10 || Math.abs(int_18) != int_10 || int_10 <= 0) {
                                BlockPos blockPos_2 = new BlockPos(int_15, int_14, int_17);
                                if (isAirOrLeaves(modifiableTestableWorld_1, blockPos_2) || isReplaceablePlant(modifiableTestableWorld_1, blockPos_2)) {
                                    this.setBlockState(set_1, modifiableTestableWorld_1, blockPos_2, LEAVES, mutableIntBoundingBox_1);
                                }
                            }
                        }
                    }

                    if (int_10 >= int_11) {
                        int_10 = int_12;
                        int_12 = 1;
                        ++int_11;
                        if (int_11 > int_4) {
                            int_11 = int_4;
                        }
                    } else {
                        ++int_10;
                    }
                }

                int_8 = random_1.nextInt(3);

                for(int_14 = 0; int_14 < int_1 - int_8; ++int_14) {
                    if (isAirOrLeaves(modifiableTestableWorld_1, blockPos_1.up(int_14))) {
                        this.setBlockState(set_1, modifiableTestableWorld_1, blockPos_1.up(int_14), LOG, mutableIntBoundingBox_1);
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    static {
        LOG = ModBlocks.LAUREL_LOG.getDefaultState();
        LEAVES = ModBlocks.LAUREL_LEAVES.getDefaultState();
    }
}
