package org.example.chessplatformbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.chessplatformbe.persistence.impl.jpa.entity")
public class ChessplatformBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessplatformBeApplication.class, args);
    }

}
