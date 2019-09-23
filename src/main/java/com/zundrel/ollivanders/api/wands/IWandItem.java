package com.zundrel.ollivanders.api.wands;

import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.woods.IWood;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface IWandItem {
    /**
     * Returns the core of this wand.
     *
     * @param stack The ItemStack with a core.
     * @return The core.
     */
    ICore getCore(ItemStack stack);

    /**
     * Sets the core of this wand.
     *
     * @param stack The ItemStack with a core.
     * @param core The core you want to set for the wand.
     */
    void setCore(ItemStack stack, ICore core);

    UUID getOwner(ItemStack stack);

    void setOwner(ItemStack stack, UUID uuid);

    EnumWandQuality getQuality(ItemStack stack);

    void setQuality(ItemStack stack, EnumWandQuality quality);

    /**
     * Returns the wood of this wand.
     *
     * @return The wand.
     */
    IWood getWood();
}
