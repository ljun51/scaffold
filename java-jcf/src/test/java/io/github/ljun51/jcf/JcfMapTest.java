package io.github.ljun51.jcf;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JcfMapTest {

    @Test
    public void sortedTest() {
        Map map = new HashMap();
        map.put("c", "c");
        map.put("a", "a");
        map.put("d", "d");
        map.put("b", "b");



        map.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }

}