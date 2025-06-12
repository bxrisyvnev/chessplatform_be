package org.example.chessplatformbe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ChessplatformApplicationTest {

    @Test
    void contextLoads() {
        // This test will fail if the Spring application context cannot start
    }
}
