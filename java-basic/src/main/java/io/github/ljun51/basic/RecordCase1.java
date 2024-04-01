package io.github.ljun51.basic;

import java.util.Objects;

/**
 * Record 关键字
 *
 * @author lee
 */
public record RecordCase1(String name, String address) {

    public RecordCase1{
        Objects.requireNonNull(name);
        Objects.requireNonNull(address);
    }

    public RecordCase1(String name) {
        this(name, "Unknown");
    }

    public String address() {
        return address + ", CN";
    }
}
