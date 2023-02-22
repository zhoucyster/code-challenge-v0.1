package com.rbc.code.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Configuration
public class AppConfig {

    @Bean
    public ReadWriteLock myLock() {
        return new ReentrantReadWriteLock();
    }
}
