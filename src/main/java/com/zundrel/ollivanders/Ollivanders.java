package com.zundrel.ollivanders;

import com.zundrel.ollivanders.common.events.CommonEventHandler;
import com.zundrel.ollivanders.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ollivanders implements ModInitializer
{
    public static final String MODID = "ollivanders";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static ItemGroup generalItemGroup = FabricItemGroupBuilder.build(new Identifier(MODID, "general"), () -> new ItemStack(ModItems.PHOENIX_FEATHER));

    @Override
    public void onInitialize() {
        CommonEventHandler.init();

        ModComponents.init();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModBiomes.init();
        ModCores.init();
        ModWoods.init();
        ModSpells.init();
        ModCommands.init();

        //ServerSidePacketRegistry.INSTANCE.register(new Identifier(MODID, "spell_projectile_packet"), );
    }
}
