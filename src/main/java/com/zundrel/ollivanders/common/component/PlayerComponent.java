package com.zundrel.ollivanders.common.component;

import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.registries.WoodRegistry;
import com.zundrel.ollivanders.api.spells.ISpell;
import com.zundrel.ollivanders.api.utils.WandUtils;
import com.zundrel.ollivanders.api.wands.IWandItem;
import com.zundrel.ollivanders.api.woods.IWood;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

import java.util.*;

public class PlayerComponent implements IPlayerComponent {
    private final PlayerEntity player;

    private IWood wood = null;
    private ICore core = null;

    private int powerLevel = 1;
    private ISpell spell = null;

    private HashMap<String, Integer> spellLevels = new HashMap<>();

    public PlayerComponent(PlayerEntity player) {
        this.player = player;

        setDestinedWand();
    }

    @Override
    public Entity getEntity() {
        return player;
    }

    @Override
    public ComponentType<?> getComponentType() {
        return OllivandersAPI.PLAYER_COMPONENT;
    }

    @Override
    public IWood getWood() {
        return wood;
    }

    @Override
    public ICore getCore() {
        return core;
    }

    @Override
    public int getPowerLevel() {
        return powerLevel;
    }

    @Override
    public int getWandMaxPowerLevel() {
        ItemStack stack = player.getMainHandStack();

        if (!stack.isEmpty() && stack.getItem() instanceof IWandItem) {
            UUID wandOwner = WandUtils.getOwnerFromStack(stack);

            if (wandOwner.equals(player.getUuid())) {
                return 5;
            } else {
                return 3;
            }
        }

        return 0;
    }

    @Override
    public ISpell getSpell() {
        return spell;
    }

    @Override
    public void setDestinedWand() {
        setWand(WoodRegistry.findDestinedWood(player.getUuid()), CoreRegistry.findDestinedCore(player.getUuid()));
    }

    @Override
    public void setWand(IWood wood, ICore core) {
        this.wood = wood;
        this.core = core;
        markDirty();
    }

    @Override
    public void setPowerLevel(int powerLevel) {
        this.powerLevel = Math.max(1, Math.min(5, powerLevel));;
        markDirty();
    }

    @Override
    public void markDirty() {

    }

    @Override
    public void addPowerLevel(int addLevels) {
        setPowerLevel(powerLevel + addLevels);
    }

    @Override
    public void subPowerLevel(int subLevels) {
        setPowerLevel(powerLevel - subLevels);
    }

    @Override
    public void setSpell(ISpell spell) {
        this.spell = spell;
        markDirty();
    }

    @Override
    public HashMap<String, Integer> getSpellLevels() {
        return spellLevels;
    }

    @Override
    public int getSpellLevel(String spell) {
        return spellLevels.get(spell);
    }

    @Override
    public void setSpellLevel(String spell, int level) {
        spellLevels.put(spell, level);
        markDirty();
    }

    @Override
    public void addSpellLevel(String spell, int addLevels) {
        if (spellLevels.containsKey(spell))
            setSpellLevel(spell, getSpellLevel(spell) + addLevels);
        else
            setSpellLevel(spell, addLevels);
    }

    @Override
    public void subSpellLevel(String spell, int subLevels) {
        if (spellLevels.containsKey(spell) && getSpellLevel(spell) >= subLevels)
            setSpellLevel(spell, getSpellLevel(spell) + subLevels);
    }

    @Override
    public void setSpellJournal(ItemStack stack) {
        List<String> spellLevelStrings = new ArrayList<String>();
        ListTag listnbt = new ListTag();

        int lineCount = 1;
        String text = "";
        for (Map.Entry<String, Integer> entry : spellLevels.entrySet()) {
            text = text.concat(entry.getKey() + ": " + entry.getValue() + "\n");

            if (lineCount == 14) {
                spellLevelStrings.add(text);
                lineCount = 0;
                text = "";
            }

            lineCount++;
        }

        if (!text.isEmpty())
            spellLevelStrings.add(text);

        spellLevelStrings.stream().map(StringTag::new).forEach(listnbt::add);

        stack.putSubTag("author", new StringTag("Ollivanders"));
        stack.putSubTag("title", new StringTag("Spell Journal"));

        stack.putSubTag("pages", listnbt);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        if (wood != null) {
            compoundTag.putString("wood", wood.getName());
        }

        if (core != null) {
            compoundTag.putString("core", core.getName());
        }

        compoundTag.putInt("powerLevel", powerLevel);

        if (spell != null) {
            compoundTag.putString("spell", spell.getName());
        }

        if (spellLevels != null) {
            CompoundTag levelsTag = new CompoundTag();

            for (Map.Entry<String, Integer> entry : spellLevels.entrySet()) {
                CompoundTag entryTag = new CompoundTag();

                entryTag.putInt("value", entry.getValue());
                levelsTag.put(entry.getKey(), entryTag);
            }

            compoundTag.put("levels", levelsTag);
        }

        return compoundTag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        wood = WoodRegistry.findWood(compoundTag.getString("wood"));
        core = CoreRegistry.findCore(compoundTag.getString("core"));

        powerLevel = compoundTag.getInt("powerLevel");

        if (compoundTag.getString("spell").isEmpty()) {
            spell = null;
        } else {
            spell = SpellRegistry.findSpell(compoundTag.getString("spell"));
        }

        CompoundTag levelsTag = compoundTag.getCompound("levels");
        for (String key : OllivandersAPI.spellNames) {
            if (!levelsTag.getCompound(key).isEmpty()) {
                int value = levelsTag.getCompound(key).getInt("value");

                spellLevels.put(key, value);
            } else {
                spellLevels.put(key, 0);
            }
        }
    }
}
