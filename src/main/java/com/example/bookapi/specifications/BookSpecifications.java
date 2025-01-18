package com.example.bookapi.specifications;

import com.example.bookapi.entities.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> buildSpecification(String title, Long categoryId, Long authorId) {
        return (root, query, cb) -> {
            query.distinct(true);

            Specification<Book> spec = Specification.where(null);

            if (title != null && !title.isEmpty()) {
                spec = spec.and((root1, query1, cb1) -> cb1.like(cb1.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                spec = spec.and((root1, query1, cb1) -> cb1.equal(root.get("category").get("id"), categoryId));
            }
            if (authorId != null) {
                spec = spec.and((root1, query1, cb1) -> cb1.equal(root.join("authorships").join("writer").get("id"), authorId));
            }

            return spec.toPredicate(root, query, cb);
        };
    }
}

