package com.example.controller;

import com.example.bookstore.Author;
import com.example.service.BookStoreClientService;
import com.google.protobuf.Descriptors;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookStoreApi {

  @Autowired
  private BookStoreClientService bookStoreClientService;

  @GetMapping("/authors/{authorId}")
  public ResponseEntity<Map<Descriptors.FieldDescriptor, Object>> getAuthor(@PathVariable Integer authorId) {
    Optional<Author> author = bookStoreClientService.getAuthor(authorId);
    return ResponseEntity.ok(author.orElseGet(Author::getDefaultInstance).getAllFields());
  }

  @GetMapping("/authors/{authorId}/books")
  public ResponseEntity<List<Map<Descriptors.FieldDescriptor, Object>>> getBooksByAuthor(@PathVariable Integer authorId)
      throws InterruptedException {
    List<Map<Descriptors.FieldDescriptor, Object>> books = bookStoreClientService.getBooksByAuthor(authorId);
    return ResponseEntity.ok(books);
  }
}
