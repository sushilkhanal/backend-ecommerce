package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    
}