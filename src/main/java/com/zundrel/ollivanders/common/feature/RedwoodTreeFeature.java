package com.zundrel.ollivanders.common.feature;

import com.mojang.datafixers.Dynamic;
import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class RedwoodTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {
   private static final BlockState LOG;
   private static final BlockState LEAVES;

   public RedwoodTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> p_i51429_1_, boolean p_i51429_2_) {
      super(p_i51429_1_, p_i51429_2_);
   }

   public boolean generate(Set<BlockPos> set_1, ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BlockPos blockPos_1, MutableIntBoundingBox mutableIntBoundingBox_1) {
      int i = random_1.nextInt(4) + 20;
      int j = 1 + random_1.nextInt(2);
      int k = i - j;
      int l = 2 + random_1.nextInt(2);
      boolean flag = true;
      if (blockPos_1.getY() >= 1 && blockPos_1.getY() + i + 1 <= 256) {
         for(int i1 = blockPos_1.getY(); i1 <= blockPos_1.getY() + 1 + i && flag; ++i1) {
            int j1;
            if (i1 - blockPos_1.getY() < j) {
               j1 = 0;
            } else {
               j1 = l;
            }

            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

            for(int k1 = blockPos_1.getX() - j1; k1 <= blockPos_1.getX() + j1 && flag; ++k1) {
               for(int l1 = blockPos_1.getZ() - j1; l1 <= blockPos_1.getZ() + j1 && flag; ++l1) {
                  if (i1 >= 0 && i1 < 256) {
                     blockpos$mutableblockpos.set(k1, i1, l1);
                     if (!isAirOrLeaves(modifiableTestableWorld_1, blockpos$mutableblockpos)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else if (isDirtOrGrass(modifiableTestableWorld_1, blockPos_1.down()) && blockPos_1.getY() < 256 - i - 1) {
            this.setToDirt(modifiableTestableWorld_1, blockPos_1.down());
            int i3 = random_1.nextInt(2);
            int j3 = 1;
            int k3 = 0;

            for(int l3 = 0; l3 <= k; ++l3) {
               int j4 = blockPos_1.getY() + i - l3;

               Ollivanders.LOGGER.info("Position is " + (l3));

               if (l3 % 6 == 0)
                   j3++;

               for(int i2 = blockPos_1.getX() - i3; i2 <= blockPos_1.getX() + i3; ++i2) {
                  int j2 = i2 - blockPos_1.getX();

                  for(int k2 = blockPos_1.getZ() - i3; k2 <= blockPos_1.getZ() + i3; ++k2) {
                     int l2 = k2 - blockPos_1.getZ();
                     if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                        BlockPos blockpos = new BlockPos(i2, j4, k2);
                        if ((k - l3) > 4) {
                            if (isAirOrLeaves(modifiableTestableWorld_1, blockpos) || isReplaceablePlant(modifiableTestableWorld_1, blockpos)) {
                                this.setBlockState(set_1, modifiableTestableWorld_1, blockpos, LEAVES, mutableIntBoundingBox_1);
                            }
                        }
                     }
                  }
               }

               if (i3 >= j3) {
                  i3 = k3;
                  k3 = j3 - 1;
               } else {
                  ++i3;
               }
            }

            int i4 = random_1.nextInt(3);

            for(int k4 = 0; k4 < i - i4; ++k4) {
               if (isAirOrLeaves(modifiableTestableWorld_1, blockPos_1.up(k4))) {
                  this.setBlockState(set_1, modifiableTestableWorld_1, blockPos_1.up(k4), LOG, mutableIntBoundingBox_1);
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
        LOG = ModBlocks.REDWOOD_LOG.getDefaultState();
        LEAVES = ModBlocks.REDWOOD_LEAVES.getDefaultState();
    }
}