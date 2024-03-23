package io.github.ljun51.cache.service;

import io.github.ljun51.cache.model.Author;

import java.util.List;

/**
 * @author lee
 */
public interface AuthorService {

    List<Author> findAll();

    Author save(Author author);

    Author update(Author author);

    void delete(long id);

    Author getById(long id);
}
