package com.example.service;

import com.example.bookstore.Author;
import com.example.bookstore.Book;
import com.example.bookstore.BookAuthorServiceGrpc;
import com.google.protobuf.Descriptors;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class BookStoreClientService {

  @GrpcClient("grpc-client-service")
  private BookAuthorServiceGrpc.BookAuthorServiceBlockingStub bookStoreBlockingServer;
  @GrpcClient("grpc-client-service")
  private BookAuthorServiceGrpc.BookAuthorServiceStub bookStoreAsyncServer;

  public Optional<Author> getAuthor(int authorId) {
    try {
      return Optional.of(bookStoreBlockingServer.getAuthor(Author.newBuilder()
          .setAuthorId(authorId)
          .build()));
    } catch (StatusRuntimeException e) {
      System.out.println(e);
      throw e;
    }
  }

  public List<Map<Descriptors.FieldDescriptor, Object>> getBooksByAuthor(int authorId) throws InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    List<Map<Descriptors.FieldDescriptor, Object>> books = new ArrayList<>();
    bookStoreAsyncServer.getBooksByAuthor(Author.newBuilder()
        .setAuthorId(authorId)
        .build(), new StreamObserver<>() {
      @Override
      public void onNext(Book book) {
        books.add(book.getAllFields());
      }

      @Override
      public void onError(Throwable throwable) {
        countDownLatch.countDown();
      }

      @Override
      public void onCompleted() {
        countDownLatch.countDown();
      }
    });
    boolean await = countDownLatch.await(1, TimeUnit.SECONDS);
    return await ? books : List.of();
  }
}
