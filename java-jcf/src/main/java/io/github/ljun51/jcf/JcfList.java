package io.github.ljun51.jcf;

import java.util.Arrays;
import java.util.List;

/**
 * Java Collections Framework - List
 *
 * @author lee
 */
public class JcfList {

    public static void main(String[] args) {
        basicUsage();
    }

    private static void basicUsage() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // 求和
        int sum = list.stream().reduce(0, Integer::sum);
        System.out.println("1. " + sum);

        // 乘积
        int product = list.stream().reduce(1, (a, b) -> a * b);
        System.out.println("2. " + product);

        // 字符串拼接
        List<String> listStr = Arrays.asList("a", "b", "c", "d", "e");
        String str = listStr.stream().reduce("", (a, b) -> a + b);
        System.out.println("3. " + str);
    }
}
