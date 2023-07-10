package tech.stystatic.commandbridge.Sockets;


import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Formatting;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static tech.stystatic.commandbridge.Sockets.TransferClient.sendPublicMessage;

public class ReadThread extends Thread {
    final AsynchronousSocketChannel clientSocketChannel;

    public ReadThread(AsynchronousSocketChannel clientSocketChannel) {
        this.clientSocketChannel = clientSocketChannel;
    }

    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // String manipulation
                Future<Integer> readval = clientSocketChannel.read(buffer);
                readval.get();

                if (readval.get() >= 0) {
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    System.out.println("Received from server: " + new String(data).trim());

                    // Run received command
                    MinecraftServer server = MinecraftClient.getInstance().getServer();
                    server.getCommandManager().executeWithPrefix(server.getCommandSource(), new String(data).trim());

                    buffer.compact();
                }
            }
        } catch (InterruptedException | ExecutionException e) {

            // Log connection
            sendPublicMessage("Connection Lost", Formatting.RED);
            System.out.println("Connection Lost");

            try {
                TransferClient.client.close(); // Close socket
            } catch (NullPointerException | IOException ignored){ignored.printStackTrace();}
        }
    }
}
