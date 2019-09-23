package com.zundrel.ollivanders.api.component;

import com.zundrel.ollivanders.api.spells.WorldSpell;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public interface IWorldComponent extends WorldSyncedComponent {
    ArrayList<WorldSpell> getStationarySpells();

    HashMap<String, WorldSpell> getFlooSpells();

    WorldSpell getStationarySpell(int i);

    WorldSpell getStationarySpell(BlockPos pos);

    WorldSpell getFlooSpell(String name);

    WorldSpell getFlooSpell(BlockPos pos);

    void setStationarySpell(int i, WorldSpell stationarySpellObject);

    void putFlooSpell(String name, WorldSpell flooSpellObject);

    void addStationarySpell(WorldSpell stationarySpellObject);

    void removeStationarySpell(WorldSpell stationarySpellObject);

    void removeStationarySpell(int i);

    void removeFlooSpell(String name);
}
