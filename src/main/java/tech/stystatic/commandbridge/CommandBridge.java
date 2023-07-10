package tech.stystatic.commandbridge;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraftforge.fml.config.ModConfig;
import tech.stystatic.commandbridge.Config.CommandBridgeConfig;
import tech.stystatic.commandbridge.Events.ServerStart;
import tech.stystatic.commandbridge.Events.ServerStop;

public class CommandBridge implements ModInitializer {
    public static final String MODID = "commandbridge";

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.COMMON, CommandBridgeConfig.SPEC, "commandbridge-common.toml");
        ServerLifecycleEvents.SERVER_STARTING.register(new ServerStart());
        ServerLifecycleEvents.SERVER_STOPPING.register(new ServerStop());
    }


}
