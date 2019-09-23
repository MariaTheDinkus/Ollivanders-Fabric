package com.zundrel.ollivanders.api.utils;

import com.zundrel.ollivanders.api.cores.ICore;
import com.zundrel.ollivanders.api.registries.CoreRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class WandUtils {
    public static ICore getCoreFromStack(ItemStack stack) {
        if (stack.hasTag()) {
            return CoreRegistry.findCore(stack.getTag().getString("core"));
        }
        return null;
    }

    public static UUID getOwnerFromStack(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getUuid("uuid");
        } else {
            stack.setTag(new CompoundTag());
            return null;
        }
    }

    public static double compareCoordinatesDistance(BlockPos player1, BlockPos player2) {
        double x = Math.abs(player1.getX() - player2.getX());
        double y = Math.abs(player1.getY() - player2.getY());
        double z = Math.abs(player1.getZ() - player2.getZ());
        return x + y + z;
    }
}
