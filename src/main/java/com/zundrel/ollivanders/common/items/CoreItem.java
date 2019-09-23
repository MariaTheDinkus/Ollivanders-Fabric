package com.zundrel.ollivanders.common.items;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.cores.ICoreItem;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class CoreItem extends Item implements ICoreItem {

    private String core;

    public CoreItem(String core) {
        super(new Item.Settings().group(Ollivanders.generalItemGroup));

        this.core = core;
    }

    @Override
    public void appendTooltip(ItemStack itemStack_1, World world_1, List<Text> list_1, TooltipContext tooltipContext_1) {
        list_1.add(new LiteralText(Formatting.GRAY + new TranslatableText("info.ollivanders.core").asString()));
    }

    @Override
    public ICore getCore() {
        return CoreRegistry.findCore(core);
    }

}
