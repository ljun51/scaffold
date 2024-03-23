package io.github.ljun51.cache.service.impl;

import io.github.ljun51.cache.model.Author;
import io.github.ljun51.cache.repositories.AuthorRepository;
import io.github.ljun51.cache.service.AuthorService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author lee
 */
@Service
@CacheConfig(cacheNames = "authorCache")
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Cacheable(cacheNames = "authors")
    public List<Author> findAll() {
        simulateSlowService();
        return authorRepository.findAll();
    }

    @CacheEvict(cacheNames = "authors", allEntries = true)
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @CacheEvict(cacheNames = "authors", allEntries = true)
    @Override
    public Author update(Author author) {
        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        if (optionalAuthor.isEmpty()) {
            return null;
        }
        Author repCustomer = optionalAuthor.get();
        repCustomer.setName(author.getName());
        repCustomer.setCountry(author.getCountry());
        return authorRepository.save(repCustomer);
    }

    @Caching(evict = {@CacheEvict(cacheNames = "author", key = "#id"),
            @CacheEvict(cacheNames = "authors", allEntries = true)})
    @Override
    public void delete(long id) {
        authorRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "author", key = "#id", unless = "#result == null")
    @Override
    public Author getById(long id) {
        simulateSlowService();
        return authorRepository.findById(id).orElse(null);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
