package com.example.airbnbApi;


import com.example.airbnbApi.auth.AuthRequest;
import com.example.airbnbApi.auth.AuthService;
import com.example.airbnbApi.auth.RegisterRequest;
import com.example.airbnbApi.category.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaAuditing
@RestController
@Log4j2
public class AirbnbApiApplication {


	public static void main(String[] args) {


		SpringApplication.run(AirbnbApiApplication.class, args);
	}



//	@Bean
//	ApplicationRunner applicationRunner(CategoryService categoryService, AuthService authService){
//		return args -> {
//			categoryService.createCategory();
//			authService.register(new RegisterRequest("test@test.com","test1","1234"),false);
//			authService.register(new RegisterRequest("test2@test.com","test2","1234"),false);
//			authService.authenticate(new AuthRequest("test@test.com","1234"));
//		};
//	}

}
