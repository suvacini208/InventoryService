package com.suva.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.suva.inventory.config.KafkaStreamsBindings;

/**
 * 
 * @author Suvacini
 *
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.suva.inventory.repository")
@EnableBinding(KafkaStreamsBindings.class)
//@EnableBinding(Processor.class)
public class InventoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
}