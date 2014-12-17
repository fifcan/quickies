package org.fifcan.quickies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories
public class Application implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*
        repository.deleteAll();

        // save a couple of users
        repository.save(new User("fifcan", "fifcan", "fifcan@email.org"));
        repository.save(new User("rom1", "rom1", "rom1@email.org"));

        // fetch all users
        System.out.println("Users found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual user
        System.out.println("User found with findByUsername('fifcan'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByUsername("fifcan"));

        System.out.println("User found with findByEmail('rom1@email.org'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByEmail("rom1@email.org"));
*/

    }
}
