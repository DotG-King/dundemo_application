package dev.example.dundemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application.yml")
@SpringBootTest
class DundemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
