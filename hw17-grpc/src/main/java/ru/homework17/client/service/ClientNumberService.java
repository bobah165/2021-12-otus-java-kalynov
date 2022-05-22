package ru.homework17.client.service;

import io.grpc.ManagedChannel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServer;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.nio.channels.Channel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientNumberService {
//    private static final Logger logger = LoggerFactory.getLogger(ClientNumberService.class);

    private final NumberServiceGrpc.NumberServiceBlockingStub stub;

    private volatile int serverResult;
    private volatile int currentResult;
    private volatile AtomicInteger currentValue = new AtomicInteger(0);

    public ClientNumberService(ManagedChannel channel) {
        this.stub = NumberServiceGrpc.newBlockingStub(channel);
    }

    public void get() {
        var requestNumbers = NumberClient.newBuilder()
                                         .setFirst(0)
                                         .setLast(10)
                                         .build();


        stub.get(requestNumbers).forEachRemaining((result) -> {
            serverResult = result.getResult();
            System.out.println("result from server is " + serverResult);

            var thread = new Thread(() -> print());
            thread.setName("current value");
            thread.setDaemon(true);
            thread.start();

        });
    }

    private synchronized void print() {

        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (currentResult == serverResult) {
                currentValue.incrementAndGet();
            } else {
                currentResult = serverResult;
                currentValue.addAndGet(serverResult + 1);
            }
            System.out.println("client is " + currentValue);
        }
    }
}
