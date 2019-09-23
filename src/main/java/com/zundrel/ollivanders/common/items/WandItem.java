package com.zundrel.ollivanders.common.items;

import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import com.zundrel.ollivanders.api.registries.WoodRegistry;
import com.zundrel.ollivanders.api.wands.EnumWandQuality;
import com.zundrel.ollivanders.api.wands.IWandItem;
import com.zundrel.ollivanders.api.wands.WandColor;
import com.zundrel.ollivanders.api.woods.IWood;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.SystemUtil;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class WandItem extends Item implements IWandItem {

    private String wood;

    public WandItem(String wood) {
        super(new Item.Settings());

        this.wood = wood;

        WandColor.setColor(this);
    }

    @Override
    public String getTranslationKey() {
        return SystemUtil.createTranslationKey("item", new Identifier(Ollivanders.MODID, "wand_" + wood));
    }

    @Override
    public ICore getCore(ItemStack stack) {
        if (stack.hasTag()) {
            return CoreRegistry.findCore(stack.getTag().getString("core"));
        }
        return null;
    }

    @Override
    public void setCore(ItemStack stack, ICore core) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putString("core", core.getName());
    }

    @Override
    public UUID getOwner(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getUuid("uuid");
        } else {
            stack.setTag(new CompoundTag());
            return null;
        }
    }

    @Override
    public void setOwner(ItemStack stack, UUID uuid) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putUuid("uuid", uuid);
    }

    @Override
    public EnumWandQuality getQuality(ItemStack stack) {
        if (stack.hasTag()) {
            return EnumWandQuality.getFromName(stack.getTag().getString("quality"));
        } else {
            stack.setTag(new CompoundTag());
            return null;
        }
    }

    @Override
    public void setQuality(ItemStack stack, EnumWandQuality quality) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        stack.getTag().putString("quality", quality.asString());
    }

    @Override
    public IWood getWood() {
        return WoodRegistry.findWood(wood);
    }

//    @Override
//    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
//        if (!(entity instanceof PlayerEntity)) {
//            return false;
//        }
//
//        PlayerEntity player = (PlayerEntity) entity;
//
//        AtomicBoolean done = new AtomicBoolean(false);
//
//        player.getCapability(OllivandersAPI.PLAYER_INFO_CAPABILITY).ifPresent(playerInfo -> {
//            if (!player.getCooldownTracker().hasCooldown(this) && !player.getEntityWorld().isRemote()) {
//                if (getOwner(stack).equals(UUID.fromString("00000000-0000-0000-0000-000000000000")) && playerInfo.getCore() == getCore(stack) && playerInfo.getWood() == getWood()) {
//                    setOwner(stack, player.getUniqueID());
//
//                    player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1F, 1F);
//                    player.sendMessage(new TranslationTextComponent("wand.ollivanders.owner"));
//                } else if (getOwner(stack).equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
//                    player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.15F, 1F);
//                }
//
//                if (playerInfo.getSpell() != null) {
//                    player.getCooldownTracker().setCooldown(this, 40);
//                    SpellHandler.cast(playerInfo.getSpell(), playerInfo.getPowerLevel(), player.getEntityWorld(), player);
//
//                    if (!playerInfo.getSpell().isMasteredSpell(player)) {
//                        playerInfo.setSpell(null);
//                    }
//                }
//
//                done.set(true);
//            } else {
//                done.set(false);
//            }
//        });
//        return done.get();
//    }


    @Override
    public void appendTooltip(ItemStack itemStack_1, World world_1, List<Text> list_1, TooltipContext tooltipContext_1) {
        list_1.add(new LiteralText("Wood: " + Formatting.GRAY  + new TranslatableText(getWood().getTranslationKey()).getString()));

        if (itemStack_1.hasTag()) {

            list_1.add(new LiteralText("Core: " + Formatting.GRAY  + new TranslatableText(getCore(itemStack_1).getTranslationKey()).getString()));

            if (world_1.getPlayerByUuid(itemStack_1.getTag().getUuid("uuid")) != null) {
                list_1.add(new LiteralText("Owner: " + Formatting.GRAY + world_1.getPlayerByUuid(itemStack_1.getTag().getUuid("uuid")).getDisplayName().asString()));
            }

            if (MinecraftClient.getInstance().options.advancedItemTooltips) {
                list_1.add(new LiteralText("Owner (UUID): " + Formatting.GRAY  + itemStack_1.getTag().getUuid("uuid").toString()));
            }

            list_1.add(new LiteralText("Quality: " + Formatting.GRAY  + new TranslatableText(getQuality(itemStack_1).getTranslationKey()).getString()));
        }
    }

    @Override
    public void appendStacks(ItemGroup itemGroup_1, DefaultedList<ItemStack> defaultedList_1) {
        if (!wood.equals("elder")) {
            for (EnumWandQuality quality : EnumWandQuality.values()) {
                for (Identifier identifier : CoreRegistry.getKeys()) {
                    if (!identifier.getPath().equals("thestral_tail_hair")) {
                        ItemStack stack = new ItemStack(this);

                        stack.setTag(new CompoundTag());

                        stack.getTag().putString("core", identifier.getPath());

                        stack.getTag().putString("quality", quality.asString());

                        if (itemGroup_1 == getCore(stack).getGroup())
                            defaultedList_1.add(stack);
                    }
                }
            }
        } else if (itemGroup_1 == Ollivanders.generalItemGroup && wood.equals("elder")) {
            ItemStack stack = new ItemStack(this);

            stack.setTag(new CompoundTag());

            stack.getTag().putString("core", "thestral_tail_hair");

            stack.getTag().putString("quality", EnumWandQuality.PERFECT.asString());

            defaultedList_1.add(stack);
        }
    }
}
