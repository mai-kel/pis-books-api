package com.example.bookapi.entities;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @OneToMany(mappedBy = "writer")
    private List<Authorship> authorships;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Authorship> getAuthorships() {
        return authorships;
    }

    public void setAuthorships(List<Authorship> authorships) {
        this.authorships = authorships;
    }
}
