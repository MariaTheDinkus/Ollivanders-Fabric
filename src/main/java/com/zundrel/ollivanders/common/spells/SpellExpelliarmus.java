package com.zundrel.ollivanders.common.spells;

import com.zundrel.ollivanders.api.spells.EnumSpellCategory;
import com.zundrel.ollivanders.api.spells.IProjectileSpell;
import com.zundrel.ollivanders.api.spells.Spell;
import com.zundrel.ollivanders.common.utils.RandomUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpellExpelliarmus extends Spell implements IProjectileSpell {
    public SpellExpelliarmus(String name, String verbalName, EnumSpellCategory spellCategory) {
        super(name, verbalName, spellCategory);
    }

    @Override
    public boolean shouldCollideFluid() {
        return false;
    }

    @Override
    public void onHitBlock(int powerLevel, World world, PlayerEntity player, BlockHitResult rayTraceResult) {}

    @Override
    public void onHitEntity(int powerLevel, World world, PlayerEntity player, EntityHitResult rayTraceResult) {
        if (rayTraceResult.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) rayTraceResult.getEntity();

            if (!livingEntity.getMainHandStack().isEmpty()) {
                castExpelliarmus(world, player, livingEntity, Hand.MAIN_HAND);
            } else if (!livingEntity.getOffHandStack().isEmpty()) {
                castExpelliarmus(world, player, livingEntity, Hand.OFF_HAND);
            }
        }
    }

    private void castExpelliarmus(World world, PlayerEntity player, LivingEntity livingEntity, Hand hand) {
        ItemEntity item = new ItemEntity(world, livingEntity.x, livingEntity.y, livingEntity.z, livingEntity.getStackInHand(hand));

        world.spawnEntity(item);

        item.setPickupDelay(40);

        double x = RandomUtils.getRangedRandom(0.1F, 0.4F);
        double y = RandomUtils.getRangedRandom(0.1F, 0.4F);
        double z = RandomUtils.getRangedRandom(0.1F, 0.4F);

        item.setVelocity(player.getPos().add(0, player.getActiveEyeHeight(player.getPose(), player.getDimensions(player.getPose())), 0).subtract(item.getPos()).normalize().multiply(1 / 4F));

        livingEntity.setStackInHand(hand, ItemStack.EMPTY);
    }
}
