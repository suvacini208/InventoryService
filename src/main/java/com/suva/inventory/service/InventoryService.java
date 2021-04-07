package com.suva.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suva.inventory.domain.DbSequence;
import com.suva.order.domain.Inventory;
import com.suva.inventory.domain.Item;
import com.suva.inventory.domain.Order;
import com.suva.inventory.repository.DbSequenceRepository;
import com.suva.inventory.repository.ItemRepository;

@Service
public class InventoryService {

	private static final String INVENTORY_UPDATED = "INVENTORY_UPDATED";

	private static final String INVENTORY_ERROR = "INVENTORY_ERROR";

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	DbSequenceRepository dbSeqRepo;

	public void addItems(List<Item> itemList) {
		itemList.forEach(item -> item.setId(getSequence()));
		itemRepository.saveAll(itemList);
	}

	public Page<Item> getAllItems(Pageable pageable) {
		return itemRepository.findAll(pageable);
	}

	/**
	 * Generate the id sequence of cart and update the sequence table
	 * 
	 * @return id
	 */
	private long getSequence() {
		DbSequence dbSeq = dbSeqRepo.findById(Item.DB_SEQ).orElse(new DbSequence());
		long seqId = dbSeq.getSeq() + 1;
		dbSeq.setSeq(seqId);
		dbSeq.setId(Item.DB_SEQ);
		dbSeqRepo.save(dbSeq);

		return seqId;
	}

	public KStream<Long, Order> updateInventory(KStream<Long, Inventory> inventoryStream) {
		return inventoryStream.map((inventoryKey, inventoryValue) -> {
			return new KeyValue<Long, Order>(inventoryKey, updateInventoryStore(inventoryValue));
		});
	}

	@Transactional
	public Order updateInventoryStore(Inventory inventoryValue) {
		
		System.out.print("Inventory" + inventoryValue.toString());
		if(inventoryValue.getItem() == null || inventoryValue.getItem().isEmpty()) {
			return new Order(inventoryValue.getId(), INVENTORY_ERROR, "No item present in the order");
		}
		Map<Long, com.suva.order.domain.Item> orderItemMap = inventoryValue.getItem().stream()
				.collect(Collectors.toMap(com.suva.order.domain.Item::getId, item -> item));
		Iterable<Item> itemIterable = itemRepository.findAllById(orderItemMap.keySet());
		List<Item> newItemList = new ArrayList<Item>();
		Map<Long, Item> itemMapFromStore = StreamSupport.stream(itemIterable.spliterator(), false)
				.collect(Collectors.toMap(Item::getId, item -> item));
		
		for (com.suva.order.domain.Item item : orderItemMap.values()) {
			Item itemFromStore = itemMapFromStore.get(item.getId());
			Item newItem = new Item();
			int quanityToBeUpdated = 0;
			
			if (itemFromStore == null || (quanityToBeUpdated = itemFromStore.getQuantity() - item.getQuantity()) < 0) {
				if (itemFromStore == null) {
					return new Order(inventoryValue.getId(), INVENTORY_ERROR,
							String.format("Item %d not present in the store", item.getId()));
				} else {
					return new Order(inventoryValue.getId(), INVENTORY_ERROR,
							String.format("Item %d not enough available", item.getId()));
				}
			} else {
				newItem.setId(item.getId());
				newItem.setDescription(item.getDescription());
				newItem.setPrice(item.getPrice());
				newItem.setQuantity(quanityToBeUpdated);

				newItemList.add(newItem);
			}
		}

		itemRepository.saveAll(newItemList);
		return new Order(inventoryValue.getId(), INVENTORY_UPDATED, null);
	}
}
