package com.example;

import java.util.Timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        Timer time = new Timer();
		ScheduledTask st = new ScheduledTask();
		time.schedule(st, 0, 5000);
    }
}