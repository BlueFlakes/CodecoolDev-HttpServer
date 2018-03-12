package com.codecooldev;

import com.codecooldev.baseclasses.ApplicationException;
import com.codecooldev.controller.Ctrl;
import com.codecooldev.view.Screen;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan("com")
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        Ctrl ctrl = ctx.getBean(Ctrl.class);
        appRunner(ctrl, args);
    }

    private static void appRunner(Ctrl ctrl, String[] args) {
        Screen.clear();

        try {
            ctrl.start(args);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
