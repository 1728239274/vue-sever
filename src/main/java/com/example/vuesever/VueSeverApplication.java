package com.example.vuesever;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.vuesever.mapper")
public class VueSeverApplication {
    public static void main(String[] args) {
        SpringApplication.run(VueSeverApplication.class, args);
    }

}
