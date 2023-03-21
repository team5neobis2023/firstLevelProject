package com.neobis.g4g.girls_for_girls;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Girls for girls backend API", version = "0.1.3", description = "basic functional"))
public class GirlsForGirlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GirlsForGirlsApplication.class, args);
	}
}
