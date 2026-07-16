package com.shuinvy.game_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GamePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamePlatformApplication.class, args);
    }

}
