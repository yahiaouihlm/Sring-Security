package us.devoxx.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//exclude = SecurityAutoConfiguration.class
//pour desactiver  les filtres de sring  security
@SpringBootApplication()
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
	}



	@Bean
	public   BCryptPasswordEncoder  bCryptPasswordEncoder (){
		return   new BCryptPasswordEncoder();
	}
}
