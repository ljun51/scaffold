package io.github.ljun51.spi.service;

public class Dog implements ServiceSpi {
    
    @Override
    public void say() {
        System.out.println("wang wang");
    }
}