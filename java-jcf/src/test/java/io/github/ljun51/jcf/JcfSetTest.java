package io.github.ljun51.jcf;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class JcfSetTest {

    @Test
    public void sortedTest() {
        Set set = new TreeSet();
        set.addAll(List.of(11, 12, 13, 4, 5, 6, 7, 8, 9, 10));
        set.forEach(System.out::println);
    }

}