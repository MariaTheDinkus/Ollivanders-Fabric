package com.zundrel.ollivanders.client.gui;

public class PowerBarScreen {
//public class PowerBarScreen extends Screen {
//    private final static ResourceLocation overlayBar = new ResourceLocation(Ollivanders.MODID,"textures/gui/powerbar.png");
//
//    private final static int ARROW_WIDTH = 12;
//    private final static int ARROW_HEIGHT = 7;
//
//    private final static int BAR_WIDTH = 180;
//    private final static int BAR_HEIGHT = 5;
//
//    private final static int STOPPER_WIDTH = 6;
//    private final static int STOPPER_HEIGHT = 7;
//
//    private Minecraft mc;
//
//    public PowerBarScreen (Minecraft mc) {
//        super (new StringTextComponent("powerbar"));
//        this.mc = mc;
//    }
//
//    public void renderPowerBar(int screenWidth, int screenHeight) {
//        World world = mc.world;
//        PlayerEntity player = mc.player;
//
//        FontRenderer fr = mc.fontRenderer;
//
//        IPlayerComponent playerInfo = player.getCapability(OllivandersAPI.PLAYER_INFO_CAPABILITY).orElse(null);
//
//        GlStateManager.pushMatrix();
//
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//
//        mc.textureManager.bindTexture(overlayBar);
//
//        final int posX = (screenWidth / 2) - (BAR_WIDTH / 2);
//        final int posY = BAR_HEIGHT;
//
//        GL11.glTranslatef(posX, posY, 0);
//
//        blit(0, 0, 0, 0, BAR_WIDTH, BAR_HEIGHT);
//
//        if (playerInfo != null && playerInfo.getSpell() != null) {
//            int clampedPowerLevel = Math.min(playerInfo.getPowerLevel(), Math.min(playerInfo.getSpell().getMaxSpellPower(player), playerInfo.getWandMaxPowerLevel()));
//            int maxPowerLevel = Math.min(playerInfo.getSpell().getMaxSpellPower(player), playerInfo.getWandMaxPowerLevel());
//            int differencePowerLevel = playerInfo.getPowerLevel() - clampedPowerLevel;
//
//            blit(0, 0, 0, BAR_HEIGHT, (int) (BAR_WIDTH*(clampedPowerLevel / 5F)), BAR_HEIGHT);
//
//            if (differencePowerLevel > 0) {
//                blit((36 * maxPowerLevel) - 3, -1, (ARROW_WIDTH * 2) + STOPPER_WIDTH, BAR_HEIGHT * 2, STOPPER_WIDTH, STOPPER_HEIGHT);
//                blit((36 * playerInfo.getPowerLevel()) - ARROW_WIDTH * 2, -1, ARROW_WIDTH, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
//            } else {
//                blit(36 * maxPowerLevel - 3, -1, ARROW_WIDTH * 2, BAR_HEIGHT * 2, STOPPER_WIDTH, STOPPER_HEIGHT);
//                blit((36 * playerInfo.getPowerLevel()) - ARROW_WIDTH * 2, -1, 0, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
//            }
//        } else if (playerInfo != null && playerInfo.getSpell() == null)
//            blit((36 * playerInfo.getPowerLevel()) - ARROW_WIDTH * 2, -1, ARROW_WIDTH, BAR_HEIGHT * 2, ARROW_WIDTH, ARROW_HEIGHT);
//
//        GlStateManager.popMatrix();
//    }
}
