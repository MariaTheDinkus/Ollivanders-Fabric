package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.ISelfSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SpellNox extends Spell implements ISelfSpell {
    public SpellNox(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 1;
    }

    @Override
    public void onSelf(int powerLevel, World world, PlayerEntity player) {
        player.addPotionEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 0, 1));
    }
}
