package com.zundrel.ollivanders.api.cores;

import net.minecraft.item.ItemGroup;

public interface ICore {
    /**
     * Returns the name of the core.
     *
     * @return Name of the core.
     */
    String getName();

    /**
     * Returns the translation key of the core.
     *
     * @return Translation key of the core.
     */
    String getTranslationKey();

    int getRarity();

    ItemGroup getGroup();
}
