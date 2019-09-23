package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.common.entity.EntitySpellProjectile;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static EntityType<EntitySpellProjectile> SPELL_PROJECTILE;

    private static int entityId = 0;

    public static void init() {
        SPELL_PROJECTILE = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(Ollivanders.MODID, "spell"),
                FabricEntityTypeBuilder.create(EntityCategory.MISC, EntitySpellProjectile::new).size(EntityDimensions.fixed(0.5F, 0.5F)).build()
        );
    }
}
