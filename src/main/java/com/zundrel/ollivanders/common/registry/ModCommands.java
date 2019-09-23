package com.zundrel.ollivanders.common.registry;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class ModCommands {
    
    public static void init() {
        CommandRegistry.INSTANCE.register(false, dispatcher -> {
            final LiteralArgumentBuilder<ServerCommandSource> root = CommandManager.literal("ollivanders");
            root.then(CommandManager.literal("wandinfo").requires(sender -> sender.hasPermissionLevel(2)).executes(ModCommands::getWandInfo));
            root.then(CommandManager.literal("getlevel").requires(sender -> sender.hasPermissionLevel(2)).then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("spell", StringArgumentType.string()).executes(ctx -> {
                ServerPlayerEntity playerEntity = EntityArgumentType.getPlayer(ctx, "player");
                String spellName = StringArgumentType.getString(ctx, "spell");

                return getLevel(ctx, playerEntity, spellName);
            }))));
            root.then(CommandManager.literal("setlevel").requires(sender -> sender.hasPermissionLevel(2)).then(CommandManager.argument("player", EntityArgumentType.player()).then(CommandManager.argument("spell", StringArgumentType.string()).then(CommandManager.argument("level", IntegerArgumentType.integer(0)).executes(ctx -> {
                ServerPlayerEntity playerEntity = EntityArgumentType.getPlayer(ctx, "player");
                String spellName = StringArgumentType.getString(ctx, "spell");
                int level = IntegerArgumentType.getInteger(ctx, "level");

                return setLevel(ctx, playerEntity, spellName, level);
            })))));
            dispatcher.register(root);
        });
    }
    
    private static int getWandInfo (CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity playerEntity = ctx.getSource().getPlayer();
            IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(playerEntity);

            ctx.getSource().sendFeedback(new TranslatableText("commands.ollivanders.wand.info", playerComponent.getWood().getName(), playerComponent.getCore().getName()), false);
        }
        catch (CommandSyntaxException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getLevel(CommandContext<ServerCommandSource> ctx, ServerPlayerEntity playerEntity, String spellName) {
        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(playerEntity);

        if (SpellRegistry.findSpell(spellName) != null) {
            int level = playerComponent.getSpellLevel(spellName);
            ctx.getSource().sendFeedback(new TranslatableText("commands.ollivanders.level.get", playerEntity.getDisplayName().asString(), spellName, level), true);
        }

        return 0;
    }

    private static int setLevel(CommandContext<ServerCommandSource> ctx, ServerPlayerEntity playerEntity, String spellName, int level) {
        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(playerEntity);

        if (SpellRegistry.findSpell(spellName) != null) {
            int prevLevel = playerComponent.getSpellLevel(spellName);
            playerComponent.setSpellLevel(spellName, level);
            ctx.getSource().sendFeedback(new TranslatableText("commands.ollivanders.level.set", playerEntity.getDisplayName().asString(), spellName, level, prevLevel), true);
            playerEntity.getCommandSource().sendFeedback(new TranslatableText("commands.ollivanders.level.receive", spellName, level, prevLevel), false);
        }

        return 0;
    }
}