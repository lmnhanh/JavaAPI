package com.demo_api;

import com.demo_api.model.UserEntity;
import com.demo_api.repository.RoleRepository;
import com.demo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Dataloader {
    @Autowired
    UserRepository userRepository;
    CommandLineRunner init(UserRepository repository, RoleRepository roleRepository){
        return args -> {
            UserEntity userEntity1 = new UserEntity("ABC", "123");
        };
    }
}
