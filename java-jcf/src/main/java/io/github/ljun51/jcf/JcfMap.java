package io.github.ljun51.jcf;

import java.util.HashMap;
import java.util.Map;

/**
 * Java Collections Framework - Map
 *
 * @author lee
 */
public class JcfMap {

    public static void main(String[] args) {

        basicUsage();
    }

    /**
     * Basic usage
     */
    private static void basicUsage() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");
        map.put("hello", "world");
        System.out.println("1. " + map);

        map.computeIfPresent("hello", (k, v) -> v.concat(" test"));
        System.out.println("2. " + map);

        map.computeIfPresent("abc", (k, v) -> v.concat(" test"));
        System.out.println("3. " + map);
    }

}
