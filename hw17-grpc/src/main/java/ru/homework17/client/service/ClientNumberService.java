package ru.homework17.client.service;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServer;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.nio.channels.Channel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientNumberService {

    private final NumberServiceGrpc.NumberServiceBlockingStub stub;

    private int serverResult;

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

        var executor = getExecutor();

        stub.get(requestNumbers).forEachRemaining((result) -> {
            serverResult = result.getResult();
            System.out.println("result from server is " + serverResult);

            executor.execute(()-> print());
        });
    }

    private void print() {

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

    private ExecutorService getExecutor() {
        return Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                var tread = Executors.defaultThreadFactory().newThread(r);
                tread.setDaemon(true);
                return tread;
            }
        });
    }
}
