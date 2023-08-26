package com.telephone.backendlignestelephoniques;

import com.telephone.backendlignestelephoniques.entities.Attribut;
import com.telephone.backendlignestelephoniques.entities.TypeLigne;
import com.telephone.backendlignestelephoniques.entities.User;
import com.telephone.backendlignestelephoniques.enums.RoleType;
import com.telephone.backendlignestelephoniques.services.Attribut.AttributService;
import com.telephone.backendlignestelephoniques.services.TypeLigne.TypeLigneService;
import com.telephone.backendlignestelephoniques.services.User.UserServices;
import com.telephone.backendlignestelephoniques.services.User.UserServicesImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
@SpringBootApplication
public class BackendLignesTelephoniquesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendLignesTelephoniquesApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserServicesImpl userServices, AttributService attributService, TypeLigneService typeLigneService) {
		return args -> {

			System.out.println("Application-Runner");



			/*Stream.of("Owani", "Sana", "Jence", "Williams").forEach(username -> {
				User user = new User();
				user.setUsername(username);
				user.setEmail(username + "@gmail.com");
				user.setPassword("admin");
				user.setRole(RoleType.ADMIN);
				user.setCreatedDate(new Date());
				userServices.saveUser(user, "Admin");
			});

			Stream.of("Hassan", "Yassine", "Aicha", "jency").forEach(username -> {
				User user = new User();
				user.setUsername(username);
				user.setEmail(username + "@gmail.com");
				user.setPassword("admin");
				user.setRole(RoleType.USER);
				user.setCreatedDate(new Date());
				userServices.saveUser(user, "Admin");
			});*/



			/*// Créer un ensemble d'attributs
			Set<Attribut> attributs = new HashSet<>();

			Stream.of("Attribut1", "Attribut2", "Attribut3").forEach(nomAttribut -> {
				Attribut attribut = new Attribut();
				attribut.setNomAttribut(nomAttribut);
				attribut.setType("Type quelconque");
				attribut.setValeurAttribut("Valeur quelconque");
				attribut = attributService.saveAttribut(attribut);  // Sauvegarder et récupérer l'objet mis à jour
				attributs.add(attribut);  // Ajouter à l'ensemble d'attributs
			});

			// Insérer des types de lignes et associer les attributs
			Stream.of("Type1", "Type2", "Type3").forEach(nomType -> {
				TypeLigne typeLigne = new TypeLigne();
				typeLigne.setNomType(nomType);
				typeLigne.setDescriptionType("Description pour " + nomType);
				typeLigne.setCreatedDate(new Date());
				typeLigne.setAttributs(Collections.singleton(attributs));  // Associer l'ensemble d'attributs
				typeLigneService.saveTypeLigne(typeLigne);  // Sauvegarder le type de ligne
			});*/
		};
	}
}

