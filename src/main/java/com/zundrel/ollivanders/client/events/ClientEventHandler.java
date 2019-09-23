package com.zundrel.ollivanders.client.events;

public class ClientEventHandler {
    private static float overlayTime = 0;

//    @SubscribeEvent
//    public static void cloneEvent(RenderGameOverlayEvent.Pre event)
//    {
//        Minecraft mc = Minecraft.getInstance();
//        PlayerEntity player = mc.player;
//        PowerBarScreen powerBarScreen = new PowerBarScreen(mc);
//
//        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
//            return;
//
//        if (overlayTime > 0)
//            overlayTime = overlayTime - event.getPartialTicks();
//
//        if (overlayTime > 0 || !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof IWandItem)
//        powerBarScreen.renderPowerBar(mc.mainWindow.getScaledWidth(), mc.mainWindow.getScaledHeight());
//    }
//
//    @SubscribeEvent
//    public static void onInputEvent(InputEvent.KeyInputEvent event)
//    {
//        Minecraft mc = Minecraft.getInstance();
//        ClientPlayerEntity player = mc.player;
//
//        if (mc.world != null && mc.player != null) {
//            if (player.getCapability(OllivandersAPI.PLAYER_INFO_CAPABILITY).isPresent()) {
//                player.getCapability(OllivandersAPI.PLAYER_INFO_CAPABILITY).ifPresent(playerInfo -> {
//
//                    if (KeybindRegistry.decPowerLevelKey.isPressed()) {
//                        if (player.isSneaking())
//                            playerInfo.setPowerLevel(1);
//                        else
//                            playerInfo.subPowerLevel(1);
//
//                        playerInfo.syncServer(player);
//                        overlayTime = 90;
//                    } else if (KeybindRegistry.incPowerLevelKey.isPressed()) {
//                        if (player.isSneaking())
//                            playerInfo.setPowerLevel(5);
//                        else
//                            playerInfo.addPowerLevel(1);
//
//                        playerInfo.syncServer(player);
//                        overlayTime = 90;
//                    }
//
//                });
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void onTooltipEvent(ItemTooltipEvent event) {
//        if (!event.getItemStack().isEmpty()) {
//
//        }
//    }
}
