package com.zundrel.ollivanders.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.zundrel.ollivanders.Ollivanders;
import com.zundrel.ollivanders.api.component.IPlayerComponent;
import com.zundrel.ollivanders.api.utils.OllivandersComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class PowerBarScreen extends Screen {
    private final static Identifier overlayBar = new Identifier(Ollivanders.MODID,"textures/gui/powerbar.png");

    private final static int ARROW_WIDTH = 12;
    private final static int ARROW_HEIGHT = 7;

    private final static int BAR_WIDTH = 180;
    private final static int BAR_HEIGHT = 5;

    private final static int STOPPER_WIDTH = 6;
    private final static int STOPPER_HEIGHT = 7;

    private MinecraftClient mc;

    public PowerBarScreen (MinecraftClient mc) {
        super (new LiteralText("powerbar"));
        this.mc = mc;
    }

    public void renderPowerBar(int screenWidth, int screenHeight) {
        World world = mc.world;
        PlayerEntity player = mc.player;

        FontManager fr = mc.getFontManager();

        IPlayerComponent playerComponent = OllivandersComponents.getPlayerComponent(player);

        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(overlayBar);

        final int posX = (screenWidth / 2) - (BAR_WIDTH / 2);
        final int posY = BAR_HEIGHT;

        GL11.glTranslatef(posX, posY, 0);

        blit(0, 0, 0, 0, BAR_WIDTH, BAR_HEIGHT);

        if (playerComponent != null && playerComponent.getSpell() != null) {
            Ollivanders.LOGGER.info(playerComponent.getPowerLevel());
            Ollivanders.LOGGER.info(playerComponent.getSpell().getMaxSpellPower(player));
            Ollivanders.LOGGER.info(playerComponent.getWandMaxPowerLevel());

            int clampedPowerLevel = Math.min(playerComponent.getPowerLevel(), Math.min(playerComponent.getSpell().getMaxSpellPower(player), playerComponent.getWandMaxPowerLevel()));
            int maxPowerLevel = Math.min(playerComponent.getSpell().getMaxSpellPower(player), playerComponent.getWandMaxPowerLevel());
            int differencePowerLevel = playerComponent.getPowerLevel() - clampedPowerLevel;

            blit(0, 0, 0, BAR_HEIGHT, (int) (BAR_WIDTH*(clampedPowerLevel / 5F)), BAR_HEIGHT);

            if (differencePowerLevel > 0) {
                blit((36 * maxPowerLevel) - 3, -1, (ARROW_WIDTH * 2) + STOPPER_WIDTH, BAR_HEIGHT * 2, STOPPER_WIDTH, STOPPER_HEIGHT);
                blit((36 * playerComponent.getPowerLevel()) - ARROW_WIDTH * 2, -1, ARROW_WIDTH, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
            } else {
                blit(36 * maxPowerLevel - 3, -1, ARROW_WIDTH * 2, BAR_HEIGHT * 2, STOPPER_WIDTH, STOPPER_HEIGHT);
                blit((36 * playerComponent.getPowerLevel()) - ARROW_WIDTH * 2, -1, 0, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
            }
        } else if (playerComponent != null && playerComponent.getSpell() == null)
            blit((36 * playerComponent.getPowerLevel()) - ARROW_WIDTH * 2, -1, ARROW_WIDTH, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);

        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }
}
