package com.suva.inventory.controller;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.suva.inventory.config.KafkaStreamsBindings;
import com.suva.order.domain.Inventory;
import com.suva.inventory.domain.Order;
import com.suva.inventory.service.InventoryService;

@Controller
public class InventoryStreamController {
	
	@Autowired
	InventoryService inventoryService;

	/*
	 * @SendTo(KafkaStreamsBindings.UPDATE_ORDER) public KStream<Long, Order>
	 * process(
	 * 
	 * @Input(KafkaStreamsBindings.UPDATE_INVENTORY) KStream<Long, Inventory>
	 * inventoryStream) { return inventoryService.updateInventory(inventoryStream);
	 * }
	 */
	
	@StreamListener(KafkaStreamsBindings.UPDATE_INVENTORY)
	@SendTo(KafkaStreamsBindings.UPDATE_ORDER)
	public KStream<Long, Order> process(/*@Input(KafkaStreamsBindings.UPDATE_INVENTORY)*/ KStream<Long, Inventory> inventoryStream) {
		return inventoryService.updateInventory(inventoryStream);
	}
}
