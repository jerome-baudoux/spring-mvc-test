package main;

import java.util.Locale;

import main.conf.DIConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Main
 * @author Jerome
 */
@Import(DIConfiguration.class)
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
    	Locale.setDefault(Locale.US);
        SpringApplication.run(Application.class, args);
    }
}
