package com.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.ecommerce.model.Wishlist;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    List<Wishlist> findByUserId(String userId);

    Optional<Wishlist> findByProductId(String productId);
    
}
