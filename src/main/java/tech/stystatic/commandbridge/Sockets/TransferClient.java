package tech.stystatic.commandbridge.Sockets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tech.stystatic.commandbridge.Config.CommandBridgeConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TransferClient extends Thread {
    public static AsynchronousSocketChannel client;

    int port;
    String ip;
    public static Boolean debounce;

    public void run() {
        // Get from Config
        port = CommandBridgeConfig.PORT.get();
        ip = CommandBridgeConfig.IPADDRESS.get();

        while (true) {
            try {
                if ((client == null) || (!client.isOpen())) {
                    // Attempt to connect to server
                    client = AsynchronousSocketChannel.open();
                    Future<Void> result = client.connect(new InetSocketAddress(ip, port));
                    result.get();

                    System.out.println("Attempted Connection");

                    // If connection is established
                    if ((client != null) && (client.isOpen())) {

                        // Start a thread to handle reading messages over socket
                        ReadThread readThread = new ReadThread(client);
                        readThread.start();

                        // Log connections
                        System.out.println("Connection Established");
                        sendPublicMessage("Connection Established", Formatting.GREEN);

                        debounce = false;
                    }
                }
            } catch (IOException | InterruptedException | ExecutionException executionException) { // Error Handling
                try {
                    client.close();
                } catch (NullPointerException | IOException ignored){}
                System.out.println("Connection Failed, Retrying...");
            }
        }
    }

    public static void sendPublicMessage(String text, Formatting format) {
        final List<ServerPlayerEntity> playerList = MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList();
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).sendMessage(Text.literal(text).formatted(Formatting.BOLD).formatted(format));
        }
    }
}
