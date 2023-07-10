package tech.stystatic.commandbridge.Events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import tech.stystatic.commandbridge.Sockets.TransferClient;

import java.io.IOException;

import static tech.stystatic.commandbridge.Events.ServerStart.transferClient;

public class ServerStop implements ServerLifecycleEvents.ServerStopping {
    @Override
    public void onServerStopping(MinecraftServer server) {
        try {
            TransferClient.client.close();
            transferClient.stop();
        } catch (NullPointerException | IOException error) {
            error.printStackTrace();
        }
    }
}
