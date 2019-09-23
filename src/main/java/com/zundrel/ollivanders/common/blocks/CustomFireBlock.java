package com.zundrel.ollivanders.common.blocks;

import net.minecraft.block.FireBlock;

public class CustomFireBlock extends FireBlock {

    public CustomFireBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false));
    }

}
