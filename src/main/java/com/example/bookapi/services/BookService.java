package com.example.bookapi.services;

import com.example.bookapi.dtos.*;
import com.example.bookapi.entities.*;
import com.example.bookapi.repositories.*;
import com.example.bookapi.specifications.BookSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final WriterRepository writerRepository;
    private final AuthorshipRepository authorshipRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       WriterRepository writerRepository,
                       AuthorshipRepository authorshipRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.writerRepository = writerRepository;
        this.authorshipRepository = authorshipRepository;
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

    public BookDTO createBook(CreateBookDTO createBookDTO) {
        // Resolve category
        Category category = categoryRepository.findById(createBookDTO.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + createBookDTO.categoryId() + " not found"));

        // Create new book
        Book book = new Book();
        book.setTitle(createBookDTO.title());
        book.setDescription(createBookDTO.description());
        book.setCategory(category);

        // Save book to get its ID for authorship relations
        Book savedBook = bookRepository.save(book);

        // Resolve authors and create Authorship entities
        List<WriterDTO> authorDTOs = List.of(); // Default empty list
        if (createBookDTO.authorIds() != null && !createBookDTO.authorIds().isEmpty()) {
            List<Writer> writers = writerRepository.findAllById(createBookDTO.authorIds());
            if (writers.size() != createBookDTO.authorIds().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some writer IDs are invalid");
            }
            // Create authorship relationships
            writers.forEach(writer -> {
                Authorship authorship = new Authorship();
                authorship.setBook(savedBook);
                authorship.setWriter(writer);
                authorshipRepository.save(authorship);
            });
            // Map writers to WriterDTOs
            authorDTOs = writers.stream()
                    .map(writer -> new WriterDTO(writer.getId(), writer.getName(), writer.getLastName()))
                    .toList();
        }

        // Return the mapped BookDTO
        return new BookDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getDescription(),
                new CategoryDTO(category.getId(), category.getName(), category.getDescription()),
                authorDTOs
        );
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
