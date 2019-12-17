package ru.flametaichou.optifinefix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class FMLEventHandler {

    public static final FMLEventHandler INSTANCE = new FMLEventHandler();
    private static long lastCheckTime = 0;
    
    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            long now = Minecraft.getSystemTime();
            if (now - lastCheckTime > 300) {
                lastCheckTime = now;
                try {
                    GameSettings.Options ofChunkLoadingEnum = GameSettings.Options.valueOf("CHUNK_LOADING");
                    if (ofChunkLoadingEnum != null) {
                        Field ofChunkLoading = GameSettings.class.getDeclaredField("ofChunkLoading");
                        if (ofChunkLoading.getInt(Minecraft.getMinecraft().gameSettings) == 2) {
                            ofChunkLoading.setInt(Minecraft.getMinecraft().gameSettings, 0);
                            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Не разрешено использование Multi-Core загрузки чанков. Значение изменено на дефолтное."));
                            //Method updateChunkLoading = GameSettings.class.getDeclaredMethod("updateChunkLoading");
                            //updateChunkLoading.invoke(new Object[]{});
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ERROR " + e.getMessage());
                }

                if (Minecraft.getMinecraft().gameSettings.gammaSetting > 1.0F) {
                    Minecraft.getMinecraft().gameSettings.gammaSetting = 1.0F;
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Не разрешено значение яркости выше 100%. Яркость была изменена на 100%."));
                }
            }
        }
    }
}
