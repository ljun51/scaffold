package io.github.ljun51.cache;

import io.github.ljun51.cache.model.Book;

/**
 * @author lee
 */
public interface BookRepository {

    Book getByIsbn(String isbn);
}
