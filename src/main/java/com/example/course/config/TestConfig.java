package com.example.course.config;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.course.entities.Admin;
import com.example.course.entities.Card;
import com.example.course.entities.User;
import com.example.course.entities.enums.CardStatus;
import com.example.course.repositories.AdminRepository;
import com.example.course.repositories.CardRepository;
import com.example.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CardRepository cartRepository;
		
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456", 0, "Maria Brown", "vsvsvsv");
		User u2 = new User(null, "Maria Brown", "maria@gmail.com", "98888fg88", "1236", 0, "Marirown", "vsvsdvsvsvsvsv");
		
		Card c1 = new Card(null, 1,"Marcola", 2, "515151615", "132", "22/2025", CardStatus.APROVADO, u2);
		Card c2 = new Card(null, 1,"Marvdvdvsdla", 2, "514151325151615", "233", "22/2025", CardStatus.RECUSADO, u1);
		
		Admin adm1 = new Admin(null, "Marcos", "2342", "709821141", "MacMAc");
		
		userRepository.saveAll(Arrays.asList(u1, u2));
		
		cartRepository.saveAll(Arrays.asList(c1, c2));
		
		adminRepository.save(adm1);
		
		
		
	}
	
	
}
