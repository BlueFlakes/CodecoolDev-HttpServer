package com.codecooldev.httpserver.services;

import org.springframework.stereotype.Service;

@Service
public class Greeter {
    public void greet() {
        System.out.println("hello world");
    }
}
