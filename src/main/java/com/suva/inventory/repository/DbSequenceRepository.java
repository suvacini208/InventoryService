package com.suva.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.suva.inventory.domain.DbSequence;

public interface DbSequenceRepository extends MongoRepository<DbSequence, String>{

}
