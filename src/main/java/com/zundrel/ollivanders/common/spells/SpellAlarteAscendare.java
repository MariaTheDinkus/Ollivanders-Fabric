package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IProjectileSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpellAlarteAscendare extends Spell implements IProjectileSpell {
    public SpellAlarteAscendare(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public boolean shouldCollideFluid() {
        return false;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) { }

    @Override
    public void onHitEntity(int powerLevel, World world, PlayerEntity player, EntityHitResult rayTraceResult) {
        rayTraceResult.getEntity().setVelocity(0, 2f / (6 - powerLevel), 0);
    }
}
