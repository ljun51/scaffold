package io.github.ljun51.spi.controller;

import java.util.ServiceLoader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ljun51.spi.service.IShout;

@RestController
public class IndexController {
    
    @RequestMapping("/test")
    public void test() {
        ServiceLoader<IShout> list = ServiceLoader.load(IShout.class);
        for (IShout s : list) {
            s.shout();
        }
    }
}