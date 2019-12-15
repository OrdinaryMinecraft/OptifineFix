package ru.flametaichou.optifinefix;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = OptifineFix.ID, name = "Optifine Fix", version = "1.0", acceptableRemoteVersions = "*")
public final class OptifineFix {

    public final static String ID = "optifinefix";

    @EventHandler
    public void load(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(FMLEventHandler.INSTANCE);
    }
}
