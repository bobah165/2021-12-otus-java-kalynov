package ru.homework17.client.service;

import io.grpc.ManagedChannel;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServiceGrpc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ClientNumberService {
    private final NumberServiceGrpc.NumberServiceBlockingStub stub;

    private int serverResult = 0;
    private int currentResult;
    private int currentValue = 0;

    private final static int FIRST_VALUE = 0;
    private final static int LAST_VALUE = 30;
    private final static int CLIENT_LOOP_COUNT = 50;

    private final AtomicBoolean isReadFromServer = new AtomicBoolean(false);

    public ClientNumberService(ManagedChannel channel) {
        this.stub = NumberServiceGrpc.newBlockingStub(channel);
    }

    public void get() {
        var requestNumbers = NumberClient.newBuilder()
                                         .setFirst(FIRST_VALUE)
                                         .setLast(LAST_VALUE)
                                         .build();

        var thread = getThread();
        thread.start();

        readFromService(requestNumbers);
    }

    private synchronized void readFromService(NumberClient requestNumbers) {
        stub.get(requestNumbers).forEachRemaining((result) -> {

            serverResult = result.getResult();
            isReadFromServer.set(true);

            notifyAll();

            System.out.println("result from server is " + serverResult);

            try {
                wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isReadFromServer.set(false);
        });
    }

    private Thread getThread() {
        return new Thread(() -> {
            try {
                writeByClient();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private synchronized void writeByClient() throws InterruptedException {
        for (int i = 0; i < CLIENT_LOOP_COUNT; i++) {

            while (!isReadFromServer.get()) {
                wait();
            }

            Thread.sleep(1000);

            if (currentResult == serverResult) {
                currentValue++;
                System.out.println("client is " + currentValue);
                wait();
            } else {
                currentResult = serverResult;
                currentValue += currentResult + 1;
                System.out.println("client is " + currentValue);
            }
        }
    }
}
