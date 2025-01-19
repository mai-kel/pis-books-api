package com.example.bookapi.services;

import com.example.bookapi.dtos.*;
import com.example.bookapi.entities.Category;
import com.example.bookapi.repositories.CategoryRepository;
import com.example.bookapi.specifications.BookSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import com.example.bookapi.repositories.BookRepository;
import com.example.bookapi.entities.Book;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BookDTO> getBooks(String title, Long categoryId, Long authorId) {
        Specification<Book> spec = BookSpecifications.buildSpecification(title, categoryId, authorId);

        return bookRepository.findAll(spec).stream()
                .map(this::mapToBookDTO)
                .toList();
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found"));
        return mapToBookDTO(book);
    }

    public BookDTO createBook(CreateBookDTO bookDTO) {
        // Category resolution
        Category category = categoryRepository.findById(bookDTO.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + bookDTO.categoryId() + " not found"));

        // Map DTO to entity
        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setDescription(bookDTO.description());
        book.setCategory(category);

        // Save and return
        return mapToBookDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found"));

        // Map fields from DTO
        mapUpdate(book, bookUpdateDTO);

        // Save and return
        return mapToBookDTO(bookRepository.save(book));
    }

    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void mapUpdate(Book book, BookUpdateDTO bookUpdateDTO) {
        if (bookUpdateDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(bookUpdateDTO.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + bookUpdateDTO.getCategoryId() + " not found"));
            book.setCategory(category);
        }

        if (bookUpdateDTO.getTitle() != null) {
            book.setTitle(bookUpdateDTO.getTitle());
        }

        if (bookUpdateDTO.getDescription() != null) {
            book.setDescription(bookUpdateDTO.getDescription());
        }
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

