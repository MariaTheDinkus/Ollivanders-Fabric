package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IProjectileSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpellAquaEructo extends Spell implements IProjectileSpell {
    public SpellAquaEructo(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 1;
    }

    @Override
    public boolean shouldCollideFluid() {
        return true;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        BlockState state = world.getBlockState(rayTraceResult.getBlockPos());
        BlockState facingState = world.getBlockState(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide()));

        if (state.getBlock() == Blocks.LAVA || world.isAir(rayTraceResult.getBlockPos()) && world.getFluidState(rayTraceResult.getBlockPos()).getFluid() == Fluids.LAVA) {
            world.setBlockState(rayTraceResult.getBlockPos(), Blocks.OBSIDIAN.getDefaultState());
        } else if (facingState.getBlock() instanceof FireBlock) {
            world.setBlockState(rayTraceResult.getBlockPos().offset(rayTraceResult.getSide()), Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public void onHitEntity(int powerLevel, World world, PlayerEntity player, EntityHitResult rayTraceResult) {}
}
