package io.github.ljun51.docker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    @RequestMapping("/")
    public String index() {
        return "";
    }

    @RequestMapping("/test")
    public String test() {
        return "UP";
    }
}