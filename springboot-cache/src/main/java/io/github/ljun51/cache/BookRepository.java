package io.github.ljun51.cache;

/**
 * @author lee
 */
public interface BookRepository {

    Book getByIsbn(String isbn);
}
