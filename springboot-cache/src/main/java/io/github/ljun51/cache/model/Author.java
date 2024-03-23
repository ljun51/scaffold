package io.github.ljun51.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author lee
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author", schema = "public")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    private String name;

    private String country;
}
