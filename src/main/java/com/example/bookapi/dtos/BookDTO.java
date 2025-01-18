package com.example.bookapi.dtos;
import java.util.List;

public record BookDTO(Long id, String title, String description, CategoryDTO category, List<WriterDTO> authors) {}
