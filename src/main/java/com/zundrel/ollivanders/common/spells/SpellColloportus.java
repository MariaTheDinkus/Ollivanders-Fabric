package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IRaytraceSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class SpellColloportus extends Spell implements IRaytraceSpell {
    public SpellColloportus(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        BlockState state = world.getBlockState(rayTraceResult.getBlockPos());
        Block block = state.getBlock();
        if (block instanceof DoorBlock && state.get(Properties.OPEN)) {
            DoorBlock doorBlock = (DoorBlock) block;
            doorBlock.setOpen(world, rayTraceResult.getBlockPos(), false);
        } else if (block instanceof TrapdoorBlock && state.get(Properties.OPEN)) {
            state = state.cycle(Properties.OPEN);
            world.setBlockState(rayTraceResult.getBlockPos(), state, 2);
            if (state.get(Properties.WATERLOGGED)) {
                world.getFluidTickScheduler().schedule(rayTraceResult.getBlockPos(), Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            int i = state.getMaterial() == Material.METAL ? 1036 : 1013;
            world.playLevelEvent(null, i, rayTraceResult.getBlockPos(), 0);
        }
    }
}
