package app;

import controller.WordCountController;
import lib.WordCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = WordCountController.class)
@ComponentScan(basePackageClasses = WordCounter.class)
public class WordCounterApp {
    public static void main(String... args) {
        SpringApplication.run(WordCounterApp.class, args);
    }
}
