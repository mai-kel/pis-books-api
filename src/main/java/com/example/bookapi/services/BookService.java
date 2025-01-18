package com.example.bookapi.services;

import com.example.bookapi.specifications.BookSpecifications;
import com.example.bookapi.dtos.BookDTO;
import com.example.bookapi.dtos.CategoryDTO;
import com.example.bookapi.dtos.WriterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import com.example.bookapi.repositories.BookRepository;
import com.example.bookapi.entities.Book;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getBooks(String title, Long categoryId, Long authorId) {
        Specification<Book> spec = BookSpecifications.buildSpecification(title, categoryId, authorId);

        return bookRepository.findAll(spec).stream()
                .map(this::mapToBookDTO)
                .toList();
    }

    private BookDTO mapToBookDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                new CategoryDTO(book.getCategory().getId(), book.getCategory().getName(), book.getCategory().getDescription()),
                book.getAuthorships().stream()
                        .map(authorship -> new WriterDTO(
                                authorship.getWriter().getId(),
                                authorship.getWriter().getName(),
                                authorship.getWriter().getLastName()))
                        .toList()
        );
    }
}

