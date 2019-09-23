package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IStationarySpell;
import com.zundrel.ollivanders.api.spells.Spell;
import com.zundrel.ollivanders.api.spells.WorldSpell;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import com.zundrel.ollivanders.common.registry.ModBlocks;
import com.zundrel.ollivanders.common.registry.ModItems;
import com.zundrel.ollivanders.common.spells.world.FlooWorldSpell;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class SpellAliquamFloo extends Spell implements IStationarySpell {
    public SpellAliquamFloo(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 1;
    }

    @Override
    public boolean isOffset() {
        return true;
    }

    @Override
    public boolean isInfinite() {
        return true;
    }

    @Override
    public int getRadius() {
        return 2;
    }

    @Override
    public int getDuration() {
        return -1;
    }

    @Override
    public void onStationaryTick(World world, WorldSpell stationarySpellObject, BlockPos pos) {
        FlooWorldSpell flooWorldSpell = (FlooWorldSpell) stationarySpellObject;
        Box box = new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);

        flooWorldSpell.updateFlooDuration(world);

        if (!world.isAir(pos) && !(stationarySpellObject.getBlockState(world).getBlock() instanceof FireBlock)) {
            stationarySpellObject.kill(world);
        }

        if (stationarySpellObject.getBlockState(world).getBlock() instanceof FireBlock || stationarySpellObject.getBlockState(world).getBlock() == ModBlocks.GREEN_FIRE) {
            for (ItemEntity entity : world.getEntities(ItemEntity.class, box)) {
                if (!entity.getStack().isEmpty() && entity.getStack().getItem() == ModItems.FLOO_POWDER) {
                    OllivandersComponents.getWorldComponent(world).markDirty();
                    flooWorldSpell.setDuration(world, 300, true);
                    flooWorldSpell.flair(world, 20);
                    world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1.5F);
                    entity.remove();
                }
            }
        }
    }
}
