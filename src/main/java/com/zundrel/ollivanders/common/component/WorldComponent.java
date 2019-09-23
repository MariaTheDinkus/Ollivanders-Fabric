package com.zundrel.ollivanders.common.component;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.OllivandersAPI;
import com.zundrel.ollivanders.api.component.IWorldComponent;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.spells.IStationarySpell;
import com.zundrel.ollivanders.api.spells.WorldSpell;
import com.zundrel.ollivanders.common.spells.world.FlooWorldSpell;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldComponent implements IWorldComponent {
    private final World world;

    private ArrayList<WorldSpell> stationarySpells = new ArrayList<WorldSpell>();

    private HashMap<String, WorldSpell> flooSpells = new HashMap<>();

    public WorldComponent(World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public ComponentType<?> getComponentType() {
        return OllivandersAPI.WORLD_COMPONENT;
    }

    public ArrayList<WorldSpell> getStationarySpells() {
        return stationarySpells;
    }

    @Override
    public HashMap<String, WorldSpell> getFlooSpells() {
        return flooSpells;
    }

    @Override
    public WorldSpell getStationarySpell(int i) {
        return stationarySpells.get(i);
    }

    @Override
    public WorldSpell getStationarySpell(BlockPos pos) {
        for (WorldSpell stationarySpellObject : stationarySpells) {
            if (stationarySpellObject.getPosition().equals(pos))
                return stationarySpellObject;
        }
        return null;
    }

    @Override
    public WorldSpell getFlooSpell(String name) {
        return flooSpells.get(name);
    }

    @Override
    public WorldSpell getFlooSpell(BlockPos pos) {
        for (WorldSpell flooSpellObject : flooSpells.values()) {
            if (flooSpellObject.getPosition().equals(pos))
                return flooSpellObject;
        }
        return null;
    }

    @Override
    public void setStationarySpell(int i, WorldSpell stationarySpellObject) {
        stationarySpells.set(i, stationarySpellObject);
    }

    @Override
    public void putFlooSpell(String name, WorldSpell flooSpellObject) {
        if (flooSpellObject instanceof FlooWorldSpell) {
            flooSpells.put(name, (FlooWorldSpell) flooSpellObject);
            flooSpellObject.flair(world, 20);
            markDirty();
        } else {
            Ollivanders.LOGGER.error("Cannot add normal world spells to the floo spell list! Do not attempt this!");
        }
    }

    @Override
    public void addStationarySpell(WorldSpell stationarySpellObject) {
        stationarySpells.add(stationarySpellObject);
        stationarySpellObject.flair(world, 20);
        markDirty();
    }

    @Override
    public void removeStationarySpell(WorldSpell stationarySpellObject) {
        stationarySpells.remove(stationarySpellObject);
        stationarySpellObject.flair(world, 20);
        markDirty();
    }

    @Override
    public void removeStationarySpell(int i) {
        stationarySpells.remove(i);
        markDirty();
    }

    @Override
    public void removeFlooSpell(String name) {
        flooSpells.remove(name);
        markDirty();
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        ListTag listTag = new ListTag();
        ListTag flooListTag = new ListTag();

        for (WorldSpell stationarySpellObject : stationarySpells) {
            listTag.add(stationarySpellObject.toTag(new CompoundTag()));
        }

        for (WorldSpell flooSpellObject : flooSpells.values()) {
            flooListTag.add(flooSpellObject.toTag(new CompoundTag()));
        }

        compoundTag.put("list", listTag);
        compoundTag.put("floolist", flooListTag);

        return compoundTag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        ListTag listTag = compoundTag.getList("list", 10);
        ListTag flooListTAG = compoundTag.getList("floolist", 10);

        for (Tag tag : listTag) {
            if (tag instanceof CompoundTag) {
                CompoundTag compound = (CompoundTag) tag;

                BlockPos pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
                WorldSpell stationarySpellObject = new WorldSpell((IStationarySpell) SpellRegistry.findSpell(compound.getString("spell")), pos, compound.getInt("radius"), compound.getInt("duration"));

                addStationarySpell(stationarySpellObject);
            }
        }

        for (Tag tag : flooListTAG) {
            if (tag instanceof CompoundTag) {
                CompoundTag compound = (CompoundTag) tag;

                BlockPos pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
                FlooWorldSpell flooSpellObject = new FlooWorldSpell(compound.getString("name"), compound.getString("verbalName"), (IStationarySpell) SpellRegistry.findSpell(compound.getString("spell")), pos, compound.getString("facing"), compound.getInt("radius"));
                flooSpellObject.setDuration(world, compound.getInt("duration"), false);

                putFlooSpell(flooSpellObject.getName(), flooSpellObject);
            }
        }
    }
}
