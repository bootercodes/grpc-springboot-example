package com.example.advice;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@net.devh.boot.grpc.server.advice.GrpcAdvice
public class GrpcAdvice {

  @GrpcExceptionHandler(StatusRuntimeException.class)
  public void handleStatusRuntimeException(StatusRuntimeException e) {
    System.out.println(e);
  }
}
