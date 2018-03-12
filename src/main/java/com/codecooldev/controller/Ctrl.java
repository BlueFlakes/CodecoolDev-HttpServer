package com.codecooldev.controller;

import com.codecooldev.baseclasses.ApplicationException;
import com.codecooldev.functionalities.Greeter;
import org.springframework.stereotype.Controller;

@Controller
public class Ctrl {
    private Greeter greeter;

    public Ctrl(Greeter greeter) {
        this.greeter = greeter;
    }

    public void start(String[] args) throws ApplicationException {
        this.greeter.greet();
    }
}
