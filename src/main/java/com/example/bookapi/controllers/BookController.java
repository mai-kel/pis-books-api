package com.example.bookapi.controllers;
import com.example.bookapi.dtos.BookDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookapi.services.BookService;

import java.util.List;
@RestController

public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/")
    public List<BookDTO> getBooks(
            Authentication authentication,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId
    ) {
        return bookService.getBooks(title, categoryId, authorId);
    }
}