package com.zundrel.ollivanders.api.spells;

import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.SystemUtil;

public class Spell implements ISpell {
    private String name;
    private String verbalName;
    private int color;
    private EnumSpellCategory category;

    public Spell(String name, String verbalName, int color, EnumSpellCategory category) {
        this.name = name;
        this.verbalName = verbalName;
        this.color = color;
        this.category = category;
    }

    public Spell(String name, String verbalName, EnumSpellCategory category) {
        this(name, verbalName, 0, category);
    }

    public Spell(String name, String verbalName) {
        this(name, verbalName, EnumSpellCategory.NONE);
    }

    public Spell(CompoundTag nbt) {
        verbalName = nbt.getString("verbalName");
        color = nbt.getInt("color");
        category = EnumSpellCategory.valueOf(nbt.getString("category"));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTranslationKey() {
        return SystemUtil.createTranslationKey("spell", SpellRegistry.SPELL.getId(this));
    }

    @Override
    public String getVerbalName() {
        return verbalName;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public EnumSpellCategory getCategory() {
        return category;
    }

    @Override
    public int getSpellLevel(PlayerEntity player) {
        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);

        return playerComponent.getSpellLevel(getName());
    }

    @Override
    public void advanceSpellLevel(PlayerEntity player) {
        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);

        playerComponent.addSpellLevel(getName(), 1);
    }

    @Override
    public int getMaxSpellPower(PlayerEntity player) {
        return 5;
    }

    @Override
    public boolean isMasteredSpell(PlayerEntity player) {
        return false;
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag.putString("name", name);
        compoundTag.putString("verbalName", verbalName);
        compoundTag.putInt("color", color);
        compoundTag.putString("category", getCategory().asString());
        
        return null;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        name = compoundTag.getString("name");
        verbalName = compoundTag.getString("verbalName");
        color = compoundTag.getInt("color");
        category = EnumSpellCategory.valueOf(compoundTag.getString("category"));
    }
}
