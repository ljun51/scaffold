package io.github.ljun51.spi.service;

public class Dog implements IShout {
    
    @Override
    public void shout() {
        System.out.println("wang wang");
    }
}