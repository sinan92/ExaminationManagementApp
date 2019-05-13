package com.aon04.backend;

import com.aon04.backend.services.ILogService;
import com.aon04.backend.services.IZipService;
import com.aon04.backend.services.LogService;
import com.aon04.backend.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BackendApplication {

    public static LogService log;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, ILogService logService, IZipService zipService) {
        return (args) -> {
            storageService.init();
            logService.init();
            zipService.init();
        };
    }
}


