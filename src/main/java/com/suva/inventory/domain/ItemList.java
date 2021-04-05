package com.suva.inventory.domain;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class ItemList {

	@NotEmpty(message = "Item list cannot be empty")
	private List<Item> item;

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

}
