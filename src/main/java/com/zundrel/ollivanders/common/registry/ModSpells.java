package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.Spell;
import com.zundrel.ollivanders.common.spells.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.explosion.Explosion;

public class ModSpells {
    public static SpellAccio ACCIO;
    public static SpellAguamenti AGUAMENTI;
    public static SpellAlarteAscendare ALARTE_ASCENDARE;
    public static SpellAliquamFloo ALIQUAM_FLOO;
    public static SpellAlohomora ALOHOMORA;
    public static SpellApparate APPARATE;
    public static SpellAquaEructo AQUA_ERUCTO;
    public static SpellBombarda BOMBARDA;
    public static SpellBombarda BOMBARDA_MAXIMA;
    public static SpellColloportus COLLOPORTUS;
    public static SpellExpelliarmus EXPELLIARMUS;
    public static SpellLumos LUMOS;
    public static SpellNox NOX;

    public static void init() {
        ACCIO = register("accio", new SpellAccio("accio", "accio", EnumSpellCategory.CHARM));
        AGUAMENTI = register("aguamenti", new SpellAguamenti("aguamenti", "aguamenti", EnumSpellCategory.CHARM));
        ALARTE_ASCENDARE = register("alarte_ascendare", new SpellAlarteAscendare("alarte_ascendare", "alarte ascendare", EnumSpellCategory.CHARM));
        ALIQUAM_FLOO = register("aliquam_floo", new SpellAliquamFloo("aliquam_floo", "aliquam floo", EnumSpellCategory.FLOO));
        ALOHOMORA = register("alohomora", new SpellAlohomora("alohomora", "alohomora", EnumSpellCategory.CHARM));
        APPARATE = register("apparate", new SpellApparate("apparate", "apparate", EnumSpellCategory.CHARM));
        AQUA_ERUCTO = register("aqua_ercuto", new SpellAquaEructo("aqua_eructo", "aqua eructo", EnumSpellCategory.CHARM));
        BOMBARDA = register("bombarda", new SpellBombarda("bombarda", "bombarda", EnumSpellCategory.CHARM, 1, Explosion.DestructionType.BREAK));
        BOMBARDA_MAXIMA = register("bombarda_maxima", new SpellBombarda("bombarda_maxima", "bombarda maxima", EnumSpellCategory.CHARM, 1.5F, Explosion.DestructionType.DESTROY));
        COLLOPORTUS = register("colloportus", new SpellColloportus("colloportus", "colloportus", EnumSpellCategory.CHARM));
        EXPELLIARMUS = register("expelliarmus", new SpellExpelliarmus("expelliarmus", "expelliarmus", EnumSpellCategory.CHARM));
        LUMOS = register("lumos", new SpellLumos("lumos", "lumos", EnumSpellCategory.CHARM));
        NOX = register("nox", new SpellNox("nox", "nox", EnumSpellCategory.CHARM));
    }

    public static <T extends Spell> T register(String name, T spell) {
        return Registry.register(SpellRegistry.SPELL, new Identifier(Ollivanders.MODID, name), spell);
    }
}
