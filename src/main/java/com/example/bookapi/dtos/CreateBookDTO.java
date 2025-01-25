package com.example.bookapi.dtos;

import java.util.List;

public record CreateBookDTO(String title, String description, Long categoryId, List<Long> authorIds) {}

