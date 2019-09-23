package com.zundrel.ollivanders.api.spells;

import com.zundrel.ollivanders.api.registries.SpellRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldSpell implements IWorldSpell {
    private String name;
    private IStationarySpell spell;
    private BlockPos position;
    private int radius;
    protected int duration;
    private boolean kill = false;

    public WorldSpell(String name, IStationarySpell spell, BlockPos position, int radius, int duration) {
        this.name = name;
        this.spell = spell;
        this.position = position;
        this.radius = radius;
        this.duration = duration;
    }
    
    public WorldSpell(IStationarySpell spell, BlockPos position, int radius, int duration) {
        this(spell.getName(), spell, position, radius, duration);
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public IStationarySpell getSpell() {
        return spell;
    }

    @Override
    public BlockPos getPosition() {
        return position;
    }

    @Override
    public int getDuration() {
        return duration;
    }
    
    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public BlockState getBlockState(World world) {
        return world.getBlockState(getPosition());
    }

    @Override
    public void age(World world)
    {
        if (duration >= 0)
            duration--;
        if (duration < 0 && !spell.isInfinite())
        {
            kill(world);
        }
    }

    @Override
    public void age(World world, int i)
    {
        duration -= i;
        if (duration < 0 && !spell.isInfinite())
        {
            kill(world);
        }
    }

    @Override
    public void kill(World world)
    {
        kill = true;
        flair(world, 20);
    }

    @Override
    public boolean isDead() {
        return kill;
    }

    @Override
    public boolean isInside(BlockPos pos) {
        return getPosition().isWithinDistance(pos, radius);
    }

    @Override
    public List<Entity> getEntities(World world) {
        List<Entity> entities = new ArrayList<Entity>();
        Box box = new Box(getPosition().getX() - radius, getPosition().getY(), getPosition().getZ() - radius, getPosition().getX() + radius, getPosition().getY() + radius, getPosition().getZ() + radius);

        return world.getEntities(Entity.class, box);
    }

    @Override
    public List<LivingEntity> getLivingEntities(World world) {
        List<Entity> entities = new ArrayList<Entity>();
        Box box = new Box(getPosition().getX() - radius, getPosition().getY(), getPosition().getZ() - radius, getPosition().getX() + radius, getPosition().getY() + radius, getPosition().getZ() + radius);

        return world.getEntities(LivingEntity.class, box);
    }

    @Override
    public List<ItemEntity> getItems(World world) {
        List<Entity> entities = new ArrayList<Entity>();
        Box box = new Box(getPosition().getX() - radius, getPosition().getY(), getPosition().getZ() - radius, getPosition().getX() + radius, getPosition().getY() + radius, getPosition().getZ() + radius);

        return world.getEntities(ItemEntity.class, box);
    }

    @Override
    public double[] vecToSpher (Vec3d vec)
    {
        double inc = Math.acos(vec.getZ());
        double azi = Math.atan2(vec.getY(), vec.getX());
        double[] ret = new double[2];
        ret[0] = inc;
        ret[1] = azi;
        return ret;
    }

    @Override
    public Vec3d spherToVec (double[] spher)
    {
        double inc = spher[0];
        double azi = spher[1];
        double x = radius * Math.sin(inc) * Math.cos(azi);
        double z = radius * Math.sin(inc) * Math.sin(azi);
        double y = radius * Math.cos(inc);
        Vec3d ret = new Vec3d(x, y, z);
        return ret;
    }

    @Override
    public void flair (World world, double d)
    {
        if (d > 10)
        {
            d = 10;
        }
        for (double inc = (Math.random() * Math.PI) / d; inc < Math.PI; inc += Math.PI / d)
        {
            for (double azi = (Math.random() * Math.PI) / d; azi < 2 * Math.PI; azi += Math.PI / d) {
                double[] spher = new double[2];
                spher[0] = inc;
                spher[1] = azi;
                Vec3d e = new Vec3d(getPosition()).add(spherToVec(spher));
                if (!world.isClient()) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, e.getX() + 0.5, e.getY(), e.getZ() + 0.5, 1, 0, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag.putString("name", name);
        compoundTag.putString("spell", spell.getName());
        compoundTag.putInt("x", getPosition().getX());
        compoundTag.putInt("y", getPosition().getY());
        compoundTag.putInt("z", getPosition().getZ());
        compoundTag.putInt("radius", radius);
        compoundTag.putInt("duration", duration);

        return compoundTag;
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        name = compoundTag.getString("name");
        spell = (IStationarySpell) SpellRegistry.findSpell(compoundTag.getString("spell"));
        position = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
        radius = compoundTag.getInt("radius");
        duration = compoundTag.getInt("duration");
    }
}
