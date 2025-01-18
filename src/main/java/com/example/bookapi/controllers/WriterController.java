package com.example.bookapi.controllers;

import com.example.bookapi.dtos.UpdateWriterDTO;
import com.example.bookapi.dtos.WriterDTO;
import com.example.bookapi.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writers")
public class WriterController {

    private final WriterService writerService;

    @Autowired
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping("/")
    public List<WriterDTO> getAllWriters() {
        return writerService.getAllWriters();
    }

    @GetMapping("/{id}/")
    public ResponseEntity<WriterDTO> getWriterById(@PathVariable Long id) {
        return writerService.getWriterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<WriterDTO> createWriter(@RequestBody WriterDTO writerDTO) {
        return ResponseEntity.ok(writerService.createWriter(writerDTO));
    }

    @PutMapping("/{id}/")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<WriterDTO> updateWriter(
            @PathVariable Long id,
            @RequestBody UpdateWriterDTO updateWriterDTO) {
        return writerService.updateWriter(id, updateWriterDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<Void> deleteWriter(@PathVariable Long id) {
        if (writerService.deleteWriter(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
