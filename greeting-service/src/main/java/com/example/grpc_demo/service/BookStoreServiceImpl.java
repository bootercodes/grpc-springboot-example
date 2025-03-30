package com.example.grpc_demo.service;

import com.example.bookstore.Author;
import com.example.bookstore.Book;
import com.example.bookstore.BookAuthorServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.stream.IntStream;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookStoreServiceImpl extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {
  @Override
  public void getAuthor(Author request, StreamObserver<Author> responseObserver) {
    if (request.getAuthorId() == 1) {
      responseObserver.onError(new StatusException(Status.INVALID_ARGUMENT));
      return;
    }
    Author author = Author.newBuilder()
        .setAuthorId(request.getAuthorId())
        .setBookId(1)
        .setFirstName("fName")
        .setLastName("lName")
        .setGender("M")
        .build();
    System.out.println(author);
    responseObserver.onNext(author);
    responseObserver.onCompleted();
  }

  @Override
  public void getBooksByAuthor(Author request, StreamObserver<Book> responseObserver) {
    long currTime = System.nanoTime();
    // Stream books
    IntStream.range(1, 10)
        .boxed()
        .map(i -> Book.newBuilder()
            .setBookId(i)
            .setAuthorId(request.getAuthorId())
            .setPages(i)
            .setPrice(i)
            .setTitle("Book - " + i)
            .build())
        .forEach(responseObserver::onNext);
    System.out.println("--here-- " + (System.nanoTime() - currTime));
    responseObserver.onCompleted();
  }
}
