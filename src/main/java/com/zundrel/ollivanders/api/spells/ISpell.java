package com.zundrel.ollivanders.api.spells;

import nerdhub.cardinal.components.api.util.NbtSerializable;
import net.minecraft.entity.player.PlayerEntity;

public interface ISpell extends NbtSerializable {
    /**
     * Returns the name of the spell.
     *
     * @return Name of the spell.
     */
    String getName();

    /**
     * Returns the verbal name of the spell.
     *
     * @return Verbal name of the spell.
     */
    String getVerbalName();

    /**
     * Returns the translation key of the spell.
     *
     * @return Translation key of the spell.
     */
    String getTranslationKey();

    /**
     * Returns the color of the spell.
     *
     * @return Verbal name of the spell.
     */
    int getColor();

    EnumSpellCategory getCategory();

    int getSpellLevel(PlayerEntity player);

    void advanceSpellLevel(PlayerEntity player);

    int getMaxSpellPower(PlayerEntity player);

    boolean isMasteredSpell(PlayerEntity player);
}
