package ru.flametaichou.optifinefix;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = OptifineFix.ID, name = "Optifine Fix", version = "1.0", acceptableRemoteVersions = "*")
public final class OptifineFix {

    public final static String ID = "optifinefix";

    @Mod.EventHandler
    public void load(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
