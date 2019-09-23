package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IRaytraceSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SpellApparate extends Spell implements IRaytraceSpell {
    public SpellApparate(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 1;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        if (rayTraceResult.getSide() == Direction.UP) {
            player.teleport(rayTraceResult.getPos().getX() + 0.5, rayTraceResult.getPos().getY() + 1, rayTraceResult.getPos().getZ() + 0.5);
        } else if (rayTraceResult.getSide() == Direction.DOWN) {
            player.teleport(rayTraceResult.getPos().getX() + 0.5, rayTraceResult.getPos().getY() - 2, rayTraceResult.getPos().getZ() + 0.5);
        } else {
            BlockPos pos = rayTraceResult.getBlockPos().offset(rayTraceResult.getSide()).down();
            player.teleport(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        }
    }
}
