package com.suva.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.suva.inventory.domain.DbSequence;
import com.suva.inventory.domain.Item;
import com.suva.inventory.repository.DbSequenceRepository;
import com.suva.inventory.repository.ItemRepository;

@Service
public class InventoryService {
	
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
}
