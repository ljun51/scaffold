package io.github.ljun51.cache.controller;

import io.github.ljun51.cache.model.Author;
import io.github.ljun51.cache.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lee
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<Object> findAll() {
        List<Author> dataList = this.authorService.findAll();
        return ResponseEntity.ok(dataList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        Author data = authorService.getById(Long.parseLong(id));
        return ResponseEntity.ok(data);
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody Author author) {
        Author created = authorService.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Author author) {
        Author updated = authorService.update(author);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        authorService.delete(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }
}
