package ru.homework17.client.service;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServer;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ClientNumberService {
    private final NumberServiceGrpc.NumberServiceStub stub;

    private AtomicInteger serverResult = new AtomicInteger(0);

    private int currentResult = 0;
    private int currentValue = 0;

    private final static int FIRST_VALUE = 0;
    private final static int LAST_VALUE = 30;
    private final static int CLIENT_LOOP_COUNT = 50;

    public ClientNumberService(ManagedChannel channel) {
        this.stub = NumberServiceGrpc.newStub(channel);
    }

    public void get() throws InterruptedException {
        var requestNumbers = NumberClient.newBuilder()
                                         .setFirst(FIRST_VALUE)
                                         .setLast(LAST_VALUE)
                                         .build();

        var startServerReadLatch = new CountDownLatch(1);
        var endServerReadLatch = new CountDownLatch(1);

        readFromService(requestNumbers, endServerReadLatch, startServerReadLatch);
        writeByClient(startServerReadLatch);

        endServerReadLatch.await();
    }

    private synchronized void readFromService(NumberClient requestNumbers,
                                              CountDownLatch endServerReadlatch,
                                              CountDownLatch startServerReadLatch) {

        stub.get(requestNumbers, new StreamObserver<NumberServer>() {
            @Override
            public void onNext(NumberServer value) {
                serverResult.getAndSet(value.getResult());
                System.out.println("server result is " + serverResult.get());
                startServerReadLatch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                endServerReadlatch.countDown();
            }
        });
    }

    private synchronized void writeByClient(CountDownLatch latch) throws InterruptedException {
        latch.await();
        for (int i = 0; i < CLIENT_LOOP_COUNT; i++) {

            Thread.sleep(1000);

            if (currentResult == serverResult.get()) {
                currentValue++;
            } else {
                currentResult = serverResult.get();
                currentValue += currentResult + 1;
            }
            System.out.println("client is " + currentValue);
        }
    }
}
