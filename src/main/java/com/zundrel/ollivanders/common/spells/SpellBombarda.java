package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IRaytraceSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SpellBombarda extends Spell implements IRaytraceSpell {
    private float modifier;
    private Explosion.DestructionType mode;

    public SpellBombarda(String name, String verbalName, EnumSpellCategory spellCategory, float modifier, Explosion.DestructionType mode) {
        super(name, verbalName, spellCategory);

        this.modifier = modifier;
        this.mode = mode;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        Vec3d vec = new Vec3d(rayTraceResult.getBlockPos().getX() + 0.5, (double) rayTraceResult.getBlockPos().getY() + 0.5, rayTraceResult.getBlockPos().getZ() + 0.5);
        vec = vec.add(rayTraceResult.getSide().getVector().getX(), rayTraceResult.getSide().getVector().getY(), rayTraceResult.getSide().getVector().getZ());

        world.createExplosion(null, vec.getX(), vec.getY(), vec.getZ(), 2.5F * (powerLevel / 5F) * modifier, mode);
    }
}
