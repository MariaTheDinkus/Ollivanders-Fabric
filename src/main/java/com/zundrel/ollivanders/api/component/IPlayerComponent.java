package com.zundrel.ollivanders.api.component;

import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.spells.ISpell;
import com.zundrel.ollivanders.api.woods.IWood;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public interface IPlayerComponent extends EntitySyncedComponent {
    IWood getWood();

    ICore getCore();

    int getPowerLevel();

    int getWandMaxPowerLevel();

    ISpell getSpell();

    void setDestinedWand();

    void setWand(IWood wood, ICore core);

    void setPowerLevel(int powerLevel);

    void addPowerLevel(int addLevels);

    void subPowerLevel(int subLevels);

    void setSpell(ISpell spell);

    HashMap<String, Integer> getSpellLevels();

    int getSpellLevel(String spell);

    void setSpellLevel(String spell, int level);

    void addSpellLevel(String spell, int addLevels);

    void subSpellLevel(String spell, int addLevels);

    void setSpellJournal(ItemStack stack);
}
