package io.github.ljun51.cache.repositories;

import io.github.ljun51.cache.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lee
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
