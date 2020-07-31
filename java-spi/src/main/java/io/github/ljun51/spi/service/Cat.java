package io.github.ljun51.spi.service;

public class Cat implements IShout {
    
    @Override
    public void shout() {
        System.out.println("miao miao");
    }
}