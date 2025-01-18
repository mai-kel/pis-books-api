package com.example.bookapi.repositories;

import com.example.bookapi.entities.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {}

