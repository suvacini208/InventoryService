package com.suva.inventory.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suva.inventory.domain.Item;
import com.suva.inventory.domain.ItemList;
import com.suva.inventory.service.InventoryService;

@RestController
@Validated
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	/**
	 * Add list of items to the inventory. Cannot add more than 10 items.
	 * @param itemList
	 */
	@PostMapping("/item")
	public void addItem(@Valid @RequestBody ItemList itemList) {
		if(itemList.getItem().size() > 10) {
			throw new InventoryException("Cannot handle more than 10 items at a time");
		}
		inventoryService.addItems(itemList.getItem());
	}
	
	/**
	 * Get list of items from the inventory with pagination
	 * @param pageable
	 * @return
	 */
	@GetMapping("/item/all")
	public Page<Item> getAllItems(Pageable pageable) {
		return inventoryService.getAllItems(pageable);
	}
}
