package ru.homework17.server;

import io.grpc.ServerBuilder;
import ru.homework17.model.Port;
import ru.homework17.server.service.ServerNumberService;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {

        var server = ServerBuilder
                .forPort(getPort())
                .addService(new ServerNumberService())
                .build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }

    private static int getPort() {
        return Integer.parseInt(Port.NUMBER_8090.getPort());
    }
}
