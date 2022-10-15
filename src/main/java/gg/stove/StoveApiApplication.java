package gg.stove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StoveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoveApiApplication.class, args);
	}
}
