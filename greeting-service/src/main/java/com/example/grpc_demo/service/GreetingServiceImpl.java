package com.example.grpc_demo.service;

import com.example.grpc.GreetingRequest;
import com.example.grpc.GreetingResponse;
import com.example.grpc.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
  @Override
  public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
    System.out.println(request.getMessage());

    responseObserver.onNext(GreetingResponse.newBuilder()
        .setMessage("Received -- " + request.getMessage())
        .build());
    responseObserver.onCompleted();
  }
}
