package com.example.bookapi.controllers;
import com.example.bookapi.dtos.BookDTO;
import com.example.bookapi.dtos.BookUpdateDTO;
import com.example.bookapi.dtos.CreateBookDTO;
import com.example.bookapi.kafka.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookapi.services.BookService;

import java.util.List;
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public BookController(BookService bookService, KafkaProducer kafkaProducer) {
        this.bookService = bookService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/")
    public List<BookDTO> getBooks(
            Authentication authentication,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId
    ) {
        return bookService.getBooks(title, categoryId, authorId);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO createBookDTO) {
        BookDTO createdBook = bookService.createBook(createBookDTO);
        kafkaProducer.sendMessage("POST", createdBook.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookUpdateDTO bookUpdateDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookUpdateDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean ifDeleted = bookService.deleteBook(id);
        if (ifDeleted){
            kafkaProducer.sendMessage("DELETE", id);
        }
        return ResponseEntity.noContent().build();
    }
}