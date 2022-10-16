package com.demo_api;

import com.demo_api.Entity.RoleEntity;
import com.demo_api.Entity.UserEntity;
import com.demo_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public CommandLineRunner dataloader(UserRepository userRepository){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				userRepository.save(new UserEntity("abc", "123", new RoleEntity("admin")));
			}
		};
	}
}
