package io.github.ljun51.basic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordCase1Test {

    @Test
    void testField() {
        RecordCase1 a = new RecordCase1("Lee", "Hunan");
        Assertions.assertEquals(a.name(), "Lee");
        Assertions.assertEquals(a.address(), "Hunan, CN");

        RecordCase1 b = new RecordCase1("Lee", "Hunan");
        Assertions.assertEquals(a, b);

        RecordCase1 c = new RecordCase1("Lee", "Hunan");
        System.out.println(c);

        RecordCase1 d = new RecordCase1("Lee");
        System.out.println(d);
    }


}
