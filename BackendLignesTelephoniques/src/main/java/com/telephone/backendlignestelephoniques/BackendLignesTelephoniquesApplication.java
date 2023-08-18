package com.telephone.backendlignestelephoniques;

import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.enums.RoleType;
import com.telephone.backendlignestelephoniques.services.User.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendLignesTelephoniquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendLignesTelephoniquesApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserServices userServices) {

		return args -> {

			/*Stream.of("Owani", "Sana", "Jence", "Williams").forEach(username -> {
				User user = new User();
				user.setUsername(username);
				user.setEmail(username+"@gmail.com");
				user.setPassword("admin");
				user.setRole(RoleType.ADMIN);
				user.setCreatedDate(new Date());
				userServices.saveUser(user, "Admin");
			});

			Stream.of("Hassan", "Yassine", "Aicha", "jency").forEach(username -> {
				User user = new User();
				user.setUsername(username);
				user.setEmail(username+"@gmail.com");
				user.setPassword("admin");
				user.setRole(RoleType.USER);
				user.setCreatedDate(new Date());
				userServices.saveUser(user,"Admin");
			});*/

		};

	}

}
