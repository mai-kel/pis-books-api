package com.example.bookapi.dtos;

import com.example.bookapi.dtos.CategoryDTO;
import com.example.bookapi.dtos.WriterDTO;

import java.util.List;

public record BookDTO(Long id, String title, String description, CategoryDTO category, List<WriterDTO> authors) {
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public CategoryDTO category() {
        return category;
    }

    @Override
    public List<WriterDTO> authors() {
        return authors;
    }
}