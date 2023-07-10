package tech.stystatic.commandbridge.Events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import tech.stystatic.commandbridge.Sockets.TransferClient;

public class ServerStart implements ServerLifecycleEvents.ServerStarting {

    public static TransferClient transferClient;
    @Override
    public void onServerStarting(MinecraftServer server) {
        transferClient = new TransferClient();
        transferClient.start();
    }
}
