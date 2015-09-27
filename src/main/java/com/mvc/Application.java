package com.mvc;

import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import com.mvc.conf.DIConfiguration;

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
