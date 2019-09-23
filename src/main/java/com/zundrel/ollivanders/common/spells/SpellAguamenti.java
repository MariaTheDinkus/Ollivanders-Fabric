package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IProjectileSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpellAguamenti extends Spell implements IProjectileSpell {
    public SpellAguamenti(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 1;
    }

    @Override
    public boolean shouldCollideFluid() {
        return false;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        BlockState state = world.getBlockState(rayTraceResult.getBlockPos());
        BlockState facingState = world.getBlockState(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide()));

        if (state.getBlock() instanceof CauldronBlock) {
            CauldronBlock cauldronBlock = (CauldronBlock) state.getBlock();

            cauldronBlock.setLevel(world, rayTraceResult.getBlockPos(), state, 3);
        } else if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) {
            BlockState oldState = state;
            world.setBlockState(rayTraceResult.getBlockPos(), oldState.with(Properties.WATERLOGGED, true));
            world.getFluidState(rayTraceResult.getBlockPos()).onScheduledTick(world, rayTraceResult.getBlockPos());
        } else if (world.isAir(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide())) || world.getFluidState(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide())).getFluid() == Fluids.FLOWING_WATER) {
            world.setBlockState(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide()), Blocks.WATER.getDefaultState());
        }
    }

    @Override
    public void onHitEntity(int powerLevel, World world, PlayerEntity player, EntityHitResult rayTraceResult) {}
}
