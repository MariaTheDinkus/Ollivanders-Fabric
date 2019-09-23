package com.zundrel.ollivanders.common.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.*;
import java.util.function.Function;

public class LargeLaurelTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {
    private static final BlockState LOG;
    private static final BlockState LEAVES;

    public LargeLaurelTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function_1, boolean boolean_1) {
        super(function_1, boolean_1);
    }

    private void makeLeafLayer(ModifiableTestableWorld modifiableTestableWorld_1, BlockPos blockPos_1, float float_1, MutableIntBoundingBox mutableIntBoundingBox_1, Set<BlockPos> set_1) {
        int int_1 = (int)((double)float_1 + 0.618D);

        for(int int_2 = -int_1; int_2 <= int_1; ++int_2) {
            for(int int_3 = -int_1; int_3 <= int_1; ++int_3) {
                if (Math.pow((double)Math.abs(int_2) + 0.5D, 2.0D) + Math.pow((double)Math.abs(int_3) + 0.5D, 2.0D) <= (double)(float_1 * float_1)) {
                    BlockPos blockPos_2 = blockPos_1.add(int_2, 0, int_3);
                    if (isAirOrLeaves(modifiableTestableWorld_1, blockPos_2)) {
                        this.setBlockState(set_1, modifiableTestableWorld_1, blockPos_2, LEAVES, mutableIntBoundingBox_1);
                    }
                }
            }
        }

    }

    private float getBaseBranchSize(int int_1, int int_2) {
        if ((float)int_2 < (float)int_1 * 0.3F) {
            return -1.0F;
        } else {
            float float_1 = (float)int_1 / 2.0F;
            float float_2 = float_1 - (float)int_2;
            float float_3 = MathHelper.sqrt(float_1 * float_1 - float_2 * float_2);
            if (float_2 == 0.0F) {
                float_3 = float_1;
            } else if (Math.abs(float_2) >= float_1) {
                return 0.0F;
            }

            return float_3 * 0.5F;
        }
    }

    private float getLeafRadiusForLayer(int int_1) {
        if (int_1 >= 0 && int_1 < 5) {
            return int_1 != 0 && int_1 != 4 ? 3.0F : 2.0F;
        } else {
            return -1.0F;
        }
    }

    private void makeLeaves(ModifiableTestableWorld modifiableTestableWorld_1, BlockPos blockPos_1, MutableIntBoundingBox mutableIntBoundingBox_1, Set<BlockPos> set_1) {
        for(int int_1 = 0; int_1 < 5; ++int_1) {
            this.makeLeafLayer(modifiableTestableWorld_1, blockPos_1.up(int_1), this.getLeafRadiusForLayer(int_1), mutableIntBoundingBox_1, set_1);
        }

    }

    private int makeOrCheckBranch(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, BlockPos blockPos_1, BlockPos blockPos_2, boolean boolean_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        if (!boolean_1 && Objects.equals(blockPos_1, blockPos_2)) {
            return -1;
        } else {
            BlockPos blockPos_3 = blockPos_2.add(-blockPos_1.getX(), -blockPos_1.getY(), -blockPos_1.getZ());
            int int_1 = this.getLongestSide(blockPos_3);
            float float_1 = (float)blockPos_3.getX() / (float)int_1;
            float float_2 = (float)blockPos_3.getY() / (float)int_1;
            float float_3 = (float)blockPos_3.getZ() / (float)int_1;

            for(int int_2 = 0; int_2 <= int_1; ++int_2) {
                BlockPos blockPos_4 = blockPos_1.add((double)(0.5F + (float)int_2 * float_1), (double)(0.5F + (float)int_2 * float_2), (double)(0.5F + (float)int_2 * float_3));
                if (boolean_1) {
                    this.setBlockState(set_1, modifiableTestableWorld_1, blockPos_4, (BlockState)LOG.with(LogBlock.AXIS, this.getLogAxis(blockPos_1, blockPos_4)), mutableIntBoundingBox_1);
                } else if (!canTreeReplace(modifiableTestableWorld_1, blockPos_4)) {
                    return int_2;
                }
            }

            return -1;
        }
    }

    private int getLongestSide(BlockPos blockPos_1) {
        int int_1 = MathHelper.abs(blockPos_1.getX());
        int int_2 = MathHelper.abs(blockPos_1.getY());
        int int_3 = MathHelper.abs(blockPos_1.getZ());
        if (int_3 > int_1 && int_3 > int_2) {
            return int_3;
        } else {
            return int_2 > int_1 ? int_2 : int_1;
        }
    }

    private Axis getLogAxis(BlockPos blockPos_1, BlockPos blockPos_2) {
        Axis direction$Axis_1 = Axis.Y;
        int int_1 = Math.abs(blockPos_2.getX() - blockPos_1.getX());
        int int_2 = Math.abs(blockPos_2.getZ() - blockPos_1.getZ());
        int int_3 = Math.max(int_1, int_2);
        if (int_3 > 0) {
            if (int_1 == int_3) {
                direction$Axis_1 = Axis.X;
            } else if (int_2 == int_3) {
                direction$Axis_1 = Axis.Z;
            }
        }

        return direction$Axis_1;
    }

    private void makeLeaves(ModifiableTestableWorld modifiableTestableWorld_1, int int_1, BlockPos blockPos_1, List<BranchPosition> list_1, MutableIntBoundingBox mutableIntBoundingBox_1, Set<BlockPos> set_1) {
        Iterator var7 = list_1.iterator();

        while(var7.hasNext()) {
            LargeLaurelTreeFeature.BranchPosition largeOakTreeFeature$BranchPosition_1 = (LargeLaurelTreeFeature.BranchPosition)var7.next();
            if (this.isHighEnough(int_1, largeOakTreeFeature$BranchPosition_1.getEndY() - blockPos_1.getY())) {
                this.makeLeaves(modifiableTestableWorld_1, largeOakTreeFeature$BranchPosition_1, mutableIntBoundingBox_1, set_1);
            }
        }

    }

    private boolean isHighEnough(int int_1, int int_2) {
        return (double)int_2 >= (double)int_1 * 0.2D;
    }

    private void makeTrunk(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, BlockPos blockPos_1, int int_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        this.makeOrCheckBranch(set_1, modifiableTestableWorld_1, blockPos_1, blockPos_1.up(int_1), true, mutableIntBoundingBox_1);
    }

    private void makeBranches(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, int int_1, BlockPos blockPos_1, List<BranchPosition> list_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        Iterator var7 = list_1.iterator();

        while(var7.hasNext()) {
            LargeLaurelTreeFeature.BranchPosition largeOakTreeFeature$BranchPosition_1 = (LargeLaurelTreeFeature.BranchPosition)var7.next();
            int int_2 = largeOakTreeFeature$BranchPosition_1.getEndY();
            BlockPos blockPos_2 = new BlockPos(blockPos_1.getX(), int_2, blockPos_1.getZ());
            if (!blockPos_2.equals(largeOakTreeFeature$BranchPosition_1) && this.isHighEnough(int_1, int_2 - blockPos_1.getY())) {
                this.makeOrCheckBranch(set_1, modifiableTestableWorld_1, blockPos_2, largeOakTreeFeature$BranchPosition_1, true, mutableIntBoundingBox_1);
            }
        }

    }

    public boolean generate(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BlockPos blockPos_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        Random random_2 = new Random(random_1.nextLong());
        int int_1 = this.getTreeHeight(set_1, modifiableTestableWorld_1, blockPos_1, 5 + random_2.nextInt(12), mutableIntBoundingBox_1);
        if (int_1 == -1) {
            return false;
        } else {
            this.setToDirt(modifiableTestableWorld_1, blockPos_1.down());
            int int_2 = (int)((double)int_1 * 0.618D);
            if (int_2 >= int_1) {
                int_2 = int_1 - 1;
            }

            double double_1 = 1.0D;
            int int_3 = (int)(1.382D + Math.pow(1.0D * (double)int_1 / 13.0D, 2.0D));
            if (int_3 < 1) {
                int_3 = 1;
            }

            int int_4 = blockPos_1.getY() + int_2;
            int int_5 = int_1 - 5;
            List<BranchPosition> list_1 = Lists.newArrayList();
            list_1.add(new LargeLaurelTreeFeature.BranchPosition(blockPos_1.up(int_5), int_4));

            for(; int_5 >= 0; --int_5) {
                float float_1 = this.getBaseBranchSize(int_1, int_5);
                if (float_1 >= 0.0F) {
                    for(int int_6 = 0; int_6 < int_3; ++int_6) {
                        double double_2 = 1.0D;
                        double double_3 = 1.0D * (double)float_1 * ((double)random_2.nextFloat() + 0.328D);
                        double double_4 = (double)(random_2.nextFloat() * 2.0F) * 3.141592653589793D;
                        double double_5 = double_3 * Math.sin(double_4) + 0.5D;
                        double double_6 = double_3 * Math.cos(double_4) + 0.5D;
                        BlockPos blockPos_2 = blockPos_1.add(double_5, (double)(int_5 - 1), double_6);
                        BlockPos blockPos_3 = blockPos_2.up(5);
                        if (this.makeOrCheckBranch(set_1, modifiableTestableWorld_1, blockPos_2, blockPos_3, false, mutableIntBoundingBox_1) == -1) {
                            int int_7 = blockPos_1.getX() - blockPos_2.getX();
                            int int_8 = blockPos_1.getZ() - blockPos_2.getZ();
                            double double_7 = (double)blockPos_2.getY() - Math.sqrt((double)(int_7 * int_7 + int_8 * int_8)) * 0.381D;
                            int int_9 = double_7 > (double)int_4 ? int_4 : (int)double_7;
                            BlockPos blockPos_4 = new BlockPos(blockPos_1.getX(), int_9, blockPos_1.getZ());
                            if (this.makeOrCheckBranch(set_1, modifiableTestableWorld_1, blockPos_4, blockPos_2, false, mutableIntBoundingBox_1) == -1) {
                                list_1.add(new LargeLaurelTreeFeature.BranchPosition(blockPos_2, blockPos_4.getY()));
                            }
                        }
                    }
                }
            }

            this.makeLeaves(modifiableTestableWorld_1, int_1, blockPos_1, list_1, mutableIntBoundingBox_1, set_1);
            this.makeTrunk(set_1, modifiableTestableWorld_1, blockPos_1, int_2, mutableIntBoundingBox_1);
            this.makeBranches(set_1, modifiableTestableWorld_1, int_1, blockPos_1, list_1, mutableIntBoundingBox_1);
            return true;
        }
    }

    private int getTreeHeight(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, BlockPos blockPos_1, int int_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
        if (!isDirtOrGrass(modifiableTestableWorld_1, blockPos_1.down())) {
            return -1;
        } else {
            int int_2 = this.makeOrCheckBranch(set_1, modifiableTestableWorld_1, blockPos_1, blockPos_1.up(int_1 - 1), false, mutableIntBoundingBox_1);
            if (int_2 == -1) {
                return int_1;
            } else {
                return int_2 < 6 ? -1 : int_2;
            }
        }
    }

    static {
        LOG = ModBlocks.LAUREL_LOG.getDefaultState();
        LEAVES = ModBlocks.LAUREL_LEAVES.getDefaultState();
    }

    static class BranchPosition extends BlockPos {
        private final int endY;

        public BranchPosition(BlockPos blockPos_1, int int_1) {
            super(blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ());
            this.endY = int_1;
        }

        public int getEndY() {
            return this.endY;
        }
    }
}
