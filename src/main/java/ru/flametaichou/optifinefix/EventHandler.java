package ru.flametaichou.optifinefix;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class EventHandler {

    @SubscribeEvent
    public void onButtonClick(GuiScreenEvent.ActionPerformedEvent event) {
        if (event.gui.getClass().getName().equals("net.minecraft.client.gui.GuiVideoSettings")) {
            if (event.button != null && event.button.id == 101) {
                try {
                    if (toggleDefaultChunkLoading()) {
                        event.gui.initGui();
                    }
                } catch (Exception e) {
                    if (Minecraft.getMinecraft().thePlayer != null) {
                        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Error: " + ExceptionUtils.getRootCauseMessage(e)));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerSpawn(EntityJoinWorldEvent event) {
        if (event.world.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (Minecraft.getMinecraft().thePlayer != null && player.getDisplayName().equals(Minecraft.getMinecraft().thePlayer.getDisplayName())) {
                try {
                    toggleDefaultChunkLoading();
                } catch (Exception e) {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Error: " + ExceptionUtils.getRootCauseMessage(e)));
                }

                if (Minecraft.getMinecraft().gameSettings.gammaSetting > 1.0F) {
                    Minecraft.getMinecraft().gameSettings.gammaSetting = 1.0F;
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Не разрешено значение яркости выше 100%. Яркость была изменена на 100%."));
                }
            }
        }
    }

    private boolean toggleDefaultChunkLoading() throws IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        Field ofChunkLoading = GameSettings.class.getDeclaredField("ofChunkLoading");
        if (ofChunkLoading.getInt(Minecraft.getMinecraft().gameSettings) == 2) {
            ofChunkLoading.setInt(Minecraft.getMinecraft().gameSettings, 0);
            if (Minecraft.getMinecraft().thePlayer != null) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("[OptiFine] Не разрешено использование Multi-Core загрузки чанков. Значение изменено на дефолтное."));
            }
            Method updateChunkLoading = GameSettings.class.getDeclaredMethod("updateChunkLoading", new Class[]{});
            updateChunkLoading.invoke(Minecraft.getMinecraft().gameSettings, new Object[]{});
            return true;
        }
        return false;
    }
}
