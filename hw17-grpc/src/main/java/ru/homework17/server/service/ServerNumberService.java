package ru.homework17.server.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.NumberClient;
import ru.otus.protobuf.generated.NumberServer;
import ru.otus.protobuf.generated.NumberServiceGrpc;

public class ServerNumberService extends NumberServiceGrpc.NumberServiceImplBase {

//    @Override
//    public void get(Empty request, StreamObserver<Number> responseObserver) {
//        var number = Number.newBuilder().setNumber(10).build();
//        responseObserver.onNext(number);
//        responseObserver.onCompleted();
//    }


    @Override
    public void get(NumberClient request, StreamObserver<NumberServer> responseObserver) {
        var firstNumber = request.getFirst();
        var lastNumber = request.getLast();

        for (int i = 0; i < (lastNumber - firstNumber); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var result = NumberServer.newBuilder()
                                     .setResult(firstNumber + (i + 1))
                                     .build();

            responseObserver.onNext(result);
        }
        responseObserver.onCompleted();
    }
}
