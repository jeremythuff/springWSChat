package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@ComponentScan
@EnableAutoConfiguration
@EnableSpringConfigured
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
