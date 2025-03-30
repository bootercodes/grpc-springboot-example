package com.example.controller;

import com.example.bookstore.Author;
import com.example.service.BookStoreClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphqlApi {

    @Autowired
    BookStoreClientService bookStoreClientService;

    @QueryMapping
    Iterable<Author> authors() {
        return List.of(bookStoreClientService.getAuthor(2).get(),
                bookStoreClientService.getAuthor(3).get());
    }

    @QueryMapping
    Author authorById(@Argument Integer id) {
        return bookStoreClientService.getAuthor(id).get();
    }

    @MutationMapping
    InputAuthor addAuthor(@Argument InputAuthor author) {
        System.out.println(author);
        return author;
    }

    record InputAuthor(Integer authorId, String firstName, String lastName, String gender, String bookId) {}
}
