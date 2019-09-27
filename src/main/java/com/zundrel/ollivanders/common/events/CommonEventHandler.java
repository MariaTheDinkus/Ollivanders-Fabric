package com.zundrel.ollivanders.common.events;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.component.IWorldComponent;
import com.zundrel.ollivanders.api.event.ItemSwingCallback;
import com.zundrel.ollivanders.api.event.ServerChatCallback;
import com.zundrel.ollivanders.api.registries.SpellRegistry;
import com.zundrel.ollivanders.api.spells.WorldSpell;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import com.zundrel.ollivanders.api.utils.WandUtils;
import com.zundrel.ollivanders.api.wands.IWandItem;
import com.zundrel.ollivanders.common.items.WandItem;
import com.zundrel.ollivanders.common.spells.SpellHandler;
import com.zundrel.ollivanders.common.spells.world.FlooWorldSpell;
import net.fabricmc.fabric.api.event.world.WorldTickCallback;
import net.minecraft.command.arguments.PosArgument;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommonEventHandler {
    public static void init() {
        WorldTickCallback.EVENT.register((world -> {
            IWorldComponent worldComponent = OllivandersComponents.getWorldComponent(world);

            List<Integer> toRemove = new ArrayList<>();
            List<String> flooToRemove = new ArrayList<>();

            for (int i = 0; i < worldComponent.getStationarySpells().size(); i++) {
                WorldSpell stationarySpellObject = worldComponent.getStationarySpell(i);

                if (stationarySpellObject != null && stationarySpellObject.getSpell() != null) {
                    stationarySpellObject.getSpell().onStationaryTick(world, stationarySpellObject, stationarySpellObject.getPosition());

                    if (stationarySpellObject.isDead()) {
                        toRemove.add(i);
                    }
                }
            }

            for (WorldSpell flooSpellObject : worldComponent.getFlooSpells().values()) {
                if (flooSpellObject != null && flooSpellObject.getSpell() != null) {
                    flooSpellObject.getSpell().onStationaryTick(world, flooSpellObject, flooSpellObject.getPosition());

                    if (flooSpellObject.isDead()) {
                        flooToRemove.add(flooSpellObject.getName());
                    }
                }
            }

            worldComponent.getStationarySpells().removeAll(toRemove);
            flooToRemove.forEach((n) -> worldComponent.removeFlooSpell(n));
        }));

        ItemSwingCallback.EVENT.register((player) -> {
            if (!player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() instanceof WandItem && !player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())) {
                ItemStack stack = player.getMainHandStack();
                IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);

                if (WandUtils.getOwnerFromStack(stack).equals(UUID.fromString("00000000-0000-0000-0000-000000000000")) && playerComponent.getCore() == WandUtils.getCoreFromStack(stack) && playerComponent.getWood() == ((IWandItem) stack.getItem()).getWood()) {
                    ((WandItem) stack.getItem()).setOwner(stack, player.getUuid());

                    player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1F, 1F);
                    if (player.getEntityWorld().isClient())
                        player.sendMessage(new TranslatableText("wand.ollivanders.owner"));
                }

                if (playerComponent.getSpell() != null) {
                    player.getItemCooldownManager().set(stack.getItem(), 40);
                    SpellHandler.cast(playerComponent.getSpell(), playerComponent.getPowerLevel(), player.getEntityWorld(), player);

                    if (!playerComponent.getSpell().isMasteredSpell(player)) {
                        playerComponent.setSpell(null);
                    }
                }
            }

            return ActionResult.PASS;
        });

        ServerChatCallback.EVENT.register((player, text) -> {
            IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);
            IWorldComponent worldComponent = OllivandersComponents.getWorldComponent(player.getEntityWorld());

            boolean equals = false;

            for (WorldSpell worldSpellObject : worldComponent.getFlooSpells().values()) {
                FlooWorldSpell flooSpellObject = (FlooWorldSpell) worldSpellObject;
                if (text.equals(flooSpellObject.getVerbalName()) && worldComponent.getFlooSpell(player.getBlockPos()) != null && !(((FlooWorldSpell) worldComponent.getFlooSpell(player.getBlockPos())).getVerbalName().equals(text)) && ((FlooWorldSpell) worldComponent.getFlooSpell(player.getBlockPos())).isActive()) {
                    BlockPos flooPos = worldComponent.getFlooSpell(flooSpellObject.getName()).getPosition();
                    ((FlooWorldSpell) worldComponent.getFlooSpell(player.getBlockPos())).setDuration(player.getEntityWorld(),0, false);
                    player.addPotionEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, (20 * 15)));
                    player.teleport(player.getServerWorld(), flooPos.getX() + 0.5, flooPos.getY(), flooPos.getZ() + 0.5, flooSpellObject.getFacing().asRotation(), 0);
                    equals = true;

                    break;
                }
            }

            if (!equals && worldComponent.getFlooSpell(player.getBlockPos()) != null && ((FlooWorldSpell) worldComponent.getFlooSpell(player.getBlockPos())).isActive()) {
                Collection<WorldSpell> randomFlooSpellObjects = worldComponent.getFlooSpells().values().stream().filter(object -> !(object.getName().equals(worldComponent.getFlooSpell(player.getBlockPos()).getName()))).collect(Collectors.toList());
                FlooWorldSpell randomLocation = (FlooWorldSpell) randomFlooSpellObjects.stream().skip((int) (randomFlooSpellObjects.size() * Math.random())).findFirst().orElse(null);
                if (randomLocation != null) {
                    BlockPos flooPos = randomLocation.getPosition();
                    ((FlooWorldSpell) worldComponent.getFlooSpell(player.getBlockPos())).setDuration(player.getEntityWorld(), 0, false);
                    player.addPotionEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, (20 * 15)));
                    player.teleport(player.getServerWorld(), flooPos.getX() + 0.5, flooPos.getY(), flooPos.getZ() + 0.5, randomLocation.getFacing().asRotation(), 0);
                }
            }

            if (SpellRegistry.isSpellVerbal(text.toLowerCase())) {
                playerComponent.setSpell(SpellRegistry.findSpellVerbal(text.toLowerCase()));
                player.addChatMessage(new TranslatableText("wand.ollivanders.chosen"), true);

                return 20;
            } else if (text.toLowerCase().startsWith("apparate")) {
                ItemStack heldStack = player.getMainHandStack();
                String[] apparate = text.toLowerCase().split(" ");
                boolean cast = false;

                if (heldStack.isEmpty() && com.zundrel.ollivanders.common.utils.RandomUtils.getRangedRandom(0, 100) >= 70)
                    cast = true;
                else if (!heldStack.isEmpty() && heldStack.getItem() instanceof IWandItem && WandUtils.getOwnerFromStack(heldStack) != null && WandUtils.getOwnerFromStack(heldStack).equals(player.getUuid()))
                    cast = true;

                if (cast && apparate.length == 4) {
                    try {
                        PosArgument location = Vec3ArgumentType.vec3().parse(new StringReader(apparate[1] + " " + apparate[2] + " " + apparate[3]));
                        BlockPos pos = location.toAbsoluteBlockPos(player.getCommandSource());

                        player.getEntityWorld().playSound(null, player.x, player.y, player.z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1.5F);
                        player.teleport(pos.getX(), pos.getY(), pos.getZ());
                        playerComponent.setSpell(null);
                        playerComponent.addSpellLevel("apparate", 1);
                        player.getEntityWorld().playSound(null, player.x, player.y, player.z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1.5F);

                        return 20;
                    } catch (CommandSyntaxException e) {
                        return 0;
                    }
                }
            }
            return -1;
        });
    }
}
