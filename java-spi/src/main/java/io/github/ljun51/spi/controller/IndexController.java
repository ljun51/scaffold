package io.github.ljun51.spi.controller;

import java.util.ServiceLoader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ljun51.spi.service.ServiceSpi;

@RestController
public class IndexController {
    
    @RequestMapping("/test")
    public void test() {
        ServiceLoader<ServiceSpi> list = ServiceLoader.load(ServiceSpi.class);
        for (ServiceSpi s : list) {
            s.say();
        }
    }
}