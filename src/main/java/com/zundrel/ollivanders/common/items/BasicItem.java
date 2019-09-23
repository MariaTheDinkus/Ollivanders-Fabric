package com.zundrel.ollivanders.common.items;

import com.zundrel.ollivanders.Ollivanders;
import net.minecraft.item.Item;

public class BasicItem extends Item {

    public BasicItem() {
        super(new Item.Settings().group(Ollivanders.generalItemGroup));
    }

}
