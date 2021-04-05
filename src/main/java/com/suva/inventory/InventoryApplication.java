package com.suva.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 
 * @author Suvacini
 *
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.suva.inventory.repository")
public class InventoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
}