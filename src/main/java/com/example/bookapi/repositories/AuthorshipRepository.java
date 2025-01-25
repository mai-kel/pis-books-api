package com.example.bookapi.repositories;

import com.example.bookapi.entities.Authorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorshipRepository extends JpaRepository<Authorship, Long> {
}
