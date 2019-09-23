package com.zundrel.ollivanders.common.registry;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.common.items.BasicItem;
import com.zundrel.ollivanders.common.items.CoreItem;
import com.zundrel.ollivanders.common.items.WandItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static BasicItem FLOO_POWDER;

    public static CoreItem DRAGON_HEARTSTRING;
    public static CoreItem PHOENIX_FEATHER;
    public static CoreItem UNICORN_TAIL_HAIR;
    public static CoreItem THESTRAL_TAIL_HAIR;

    public static WandItem WAND_OAK;
    public static WandItem WAND_SPRUCE;
    public static WandItem WAND_BIRCH;
    public static WandItem WAND_JUNGLE;
    public static WandItem WAND_ACACIA;
    public static WandItem WAND_DARK_OAK;
    public static WandItem WAND_REDWOOD;
    public static WandItem WAND_ELDER;
    public static WandItem WAND_VINE;
    public static WandItem WAND_LAUREL;

    public static void init() {
        FLOO_POWDER = register("floo_powder", new BasicItem());

        DRAGON_HEARTSTRING = register("dragon_heartstring", new CoreItem("dragon_heartstring"));
        PHOENIX_FEATHER = register("phoenix_feather", new CoreItem("phoenix_feather"));
        UNICORN_TAIL_HAIR = register("unicorn_tail_hair", new CoreItem("unicorn_tail_hair"));
        THESTRAL_TAIL_HAIR = register("thestral_tail_hair", new CoreItem("thestral_tail_hair"));

        WAND_OAK = register("wand_oak", new WandItem("oak"));
        WAND_SPRUCE = register("wand_spruce", new WandItem("spruce"));
        WAND_BIRCH = register("wand_birch", new WandItem("birch"));
        WAND_JUNGLE = register("wand_jungle", new WandItem("jungle"));
        WAND_ACACIA = register("wand_acacia", new WandItem("acacia"));
        WAND_DARK_OAK = register("wand_dark_oak", new WandItem("dark_oak"));
        WAND_REDWOOD = register("wand_redwood", new WandItem("redwood"));
        WAND_ELDER = register("wand_elder", new WandItem("elder"));
        WAND_VINE = register("wand_vine", new WandItem("vine"));
        WAND_LAUREL = register("wand_laurel", new WandItem("laurel"));
    }

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, new Identifier(Ollivanders.MODID, name), item);
    }
}
