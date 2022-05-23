package ru.homework17.client.service;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServer;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.nio.channels.Channel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientNumberService {

    private final NumberServiceGrpc.NumberServiceBlockingStub stub;

    private volatile int serverResult;

    private int currentResult;
    private int currentValue = 0;

    private final static int FIRST_VALUE = 0;
    private final static int LAST_VALUE = 30;
    private final static int CLIENT_LOOP_COUNT = 50;

    public ClientNumberService(ManagedChannel channel) {
        this.stub = NumberServiceGrpc.newBlockingStub(channel);
    }

    public void get() {
        var requestNumbers = NumberClient.newBuilder()
                                         .setFirst(FIRST_VALUE)
                                         .setLast(LAST_VALUE)
                                         .build();

        stub.get(requestNumbers).forEachRemaining((result) -> {
            serverResult = result.getResult();
            System.out.println("result from server is " + serverResult);

            var thread = new Thread(() -> print());
            thread.setDaemon(true);
            thread.start();
        });
    }

    private synchronized void print() {

        for (int i = 0; i < CLIENT_LOOP_COUNT; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (currentResult == serverResult) {
                currentValue++;
            } else {
                currentResult = serverResult;
                currentValue += currentResult + 1;
            }
            System.out.println("client is " + currentValue);
        }
    }
}
