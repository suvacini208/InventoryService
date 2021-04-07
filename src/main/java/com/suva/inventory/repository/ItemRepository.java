package com.suva.inventory.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.suva.inventory.domain.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
	List<Item> findByIdIn(List<Long> id);
}
