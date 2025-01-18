package com.example.bookapi.services;

import com.example.bookapi.dtos.UpdateWriterDTO;
import com.example.bookapi.dtos.WriterDTO;
import com.example.bookapi.entities.Writer;
import com.example.bookapi.repositories.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private final WriterRepository writerRepository;

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<WriterDTO> getAllWriters() {
        return writerRepository.findAll().stream()
                .map(writer -> new WriterDTO(writer.getId(), writer.getName(), writer.getLastName()))
                .toList();
    }

    public Optional<WriterDTO> getWriterById(Long id) {
        return writerRepository.findById(id)
                .map(writer -> new WriterDTO(writer.getId(), writer.getName(), writer.getLastName()));
    }

    public WriterDTO createWriter(WriterDTO writerDTO) {
        Writer writer = new Writer();
        writer.setName(writerDTO.name());
        writer.setLastName(writerDTO.lastName());
        writer = writerRepository.save(writer);
        return new WriterDTO(writer.getId(), writer.getName(), writer.getLastName());
    }

    public Optional<WriterDTO> updateWriter(Long id, UpdateWriterDTO updateWriterDTO) {
        return writerRepository.findById(id).map(writer -> {
            writer.setName(updateWriterDTO.name());
            writer.setLastName(updateWriterDTO.lastName());
            writer = writerRepository.save(writer);
            return new WriterDTO(writer.getId(), writer.getName(), writer.getLastName());
        });
    }

    public boolean deleteWriter(Long id) {
        if (writerRepository.existsById(id)) {
            writerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

