package com.zundrel.ollivanders.common.spells.world;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.spells.IStationarySpell;
import com.zundrel.ollivanders.api.spells.WorldSpell;
import com.zundrel.ollivanders.common.blocks.CustomFireBlock;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FlooWorldSpell extends WorldSpell {
    private String verbalName;
    private String facing;

    public FlooWorldSpell(String name, String verbalName, IStationarySpell spell, BlockPos location, String facing, int radius) {
        super (name, spell, location, radius, 1);
        this.verbalName = verbalName;
        this.facing = facing;
        Ollivanders.LOGGER.info(facing);
    }

    public String getVerbalName() {
        return verbalName;
    }

    public void updateFlooDuration(World world) {
        if (duration <= 0 && world.getBlockState(getPosition()).getBlock() instanceof CustomFireBlock) {
            world.setBlockState(getPosition(), Blocks.FIRE.getDefaultState(), 1 | 2);
        } else {
            duration--;
        }
    }

    public void setDuration(World world, int duration, boolean makeGreen) {
        this.duration = duration;

        if (makeGreen)
            world.setBlockState(getPosition(), ModBlocks.GREEN_FIRE.getDefaultState(), 1 | 2);
    }

    public boolean isActive() {
        return duration > 0;
    }

    public Direction getFacing() {
        return Direction.byName(facing);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag = super.toTag(compoundTag);

        compoundTag.putString("verbalName", verbalName);
        compoundTag.putString("facing", facing);

        return compoundTag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        super.fromTag(compoundTag);

        verbalName = compoundTag.getString("verbalName");
        facing = compoundTag.getString("facing");
    }
}
