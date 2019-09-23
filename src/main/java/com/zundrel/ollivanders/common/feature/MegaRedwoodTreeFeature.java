package com.zundrel.ollivanders.common.feature;

import com.mojang.datafixers.Dynamic;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.MegaTreeFeature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class MegaRedwoodTreeFeature extends MegaTreeFeature<DefaultFeatureConfig> {
    private static final BlockState LOG;
    private static final BlockState LEAVES;
    private final boolean useBaseHeight;

    public MegaRedwoodTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, boolean useBaseHeightIn) {
        super(configFactoryIn, doBlockNotifyOnPlace, 20, 15, LOG, LEAVES);
        this.useBaseHeight = useBaseHeightIn;
    }

    @Override
    public boolean generate(Set<BlockPos> changedBlocks, ModifiableTestableWorld worldIn, Random rand, BlockPos position, MutableIntBoundingBox boundsIn) {
        int i = this.getHeight(rand);
        if (!this.checkTreeFitsAndReplaceGround(worldIn, position, i)) {
            return false;
        } else {
            this.makeFoliage(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand, boundsIn, changedBlocks);

            for(int j = 0; j < i; ++j) {
                if (isAirOrLeaves(worldIn, position.up(j))) {
                    this.setBlockState(changedBlocks, worldIn, position.up(j), LOG, boundsIn);
                }

                if (j < i - 1) {
                    if (isAirOrLeaves(worldIn, position.add(1, j, 0))) {
                        this.setBlockState(changedBlocks, worldIn, position.add(1, j, 0), LOG, boundsIn);
                    }

                    if (isAirOrLeaves(worldIn, position.add(1, j, 1))) {
                        this.setBlockState(changedBlocks, worldIn, position.add(1, j, 1), LOG, boundsIn);
                    }

                    if (isAirOrLeaves(worldIn, position.add(0, j, 1))) {
                        this.setBlockState(changedBlocks, worldIn, position.add(0, j, 1), LOG, boundsIn);
                    }
                }
            }

            return true;
        }
    }

    private void makeFoliage(ModifiableTestableWorld worldIn, int posX, int posZ, int posY, int p_214596_5_, Random random, MutableIntBoundingBox boundsIn, Set<BlockPos> changedBlocks) {
        int i = random.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        int j = 0;

        for(int k = posY - i; k <= posY; ++k) {
            int l = posY - k;
            int i1 = p_214596_5_ + MathHelper.floor((float)l / (float)i * 3.5F);
            int i2 = (l > 0 && i1 == j && (k & 1) == 0 ? 2 : 1);

            this.makeSquaredLeafLayer(worldIn, new BlockPos(posX, k, posZ), i1 + i2, boundsIn, changedBlocks);
            j = i1;
        }
    }

    static {
        LOG = ModBlocks.REDWOOD_LOG.getDefaultState();
        LEAVES = ModBlocks.REDWOOD_LEAVES.getDefaultState();
    }
}