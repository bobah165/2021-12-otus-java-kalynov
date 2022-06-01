package ru.homework17.client;

import io.grpc.ManagedChannelBuilder;
import ru.homework17.client.service.ClientNumberService;
import ru.homework17.model.Port;

public class Client {

    private static final String SERVER_HOST = "localhost";

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forTarget(getTarget())
                                           .usePlaintext()
                                           .build();
        var client = new ClientNumberService(channel);
        client.get();
        channel.shutdown();
    }

    private static String getTarget() {
        return SERVER_HOST + ":" + Port.NUMBER_8090.getPort();
    }
}
