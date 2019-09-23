package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IRaytraceSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SpellAccio extends Spell implements IRaytraceSpell {
    public SpellAccio(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {
        Box box = new Box(rayTraceResult.getPos().getX() - 0.5, rayTraceResult.getPos().getY() - 0.5, rayTraceResult.getPos().getZ() - 0.5, rayTraceResult.getPos().getX() + 1.5, rayTraceResult.getPos().getY() + 1.5, rayTraceResult.getPos().getZ() + 1.5);
        ArrayList<ItemEntity> itemEntities = (ArrayList<ItemEntity>) world.getEntities(ItemEntity.class, box.offset(rayTraceResult.getSide().getVector().getX(), rayTraceResult.getSide().getVector().getY(), rayTraceResult.getSide().getVector().getZ()));

        for (ItemEntity entity : itemEntities) {
            entity.setVelocity(player.getPos().add(0, player.getActiveEyeHeight(player.getPose(), player.getDimensions(player.getPose())), 0).subtract(entity.getPos()).normalize());
        }
    }
}
