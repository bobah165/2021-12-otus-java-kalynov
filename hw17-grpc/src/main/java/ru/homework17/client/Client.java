package ru.homework17.client;

import io.grpc.ManagedChannelBuilder;
import ru.homework17.client.service.ClientNumberService;

public class Client {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8090;

    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                                           .usePlaintext()
                                           .build();
        var client = new ClientNumberService(channel);
        client.get();
        channel.shutdown();
    }
}
