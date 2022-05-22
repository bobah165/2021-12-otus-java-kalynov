package ru.homework17.server;

import io.grpc.ServerBuilder;
import ru.homework17.server.service.ServerNumberService;

import java.io.IOException;

public class Server {

    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) throws IOException, InterruptedException {

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new ServerNumberService())
                .build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
