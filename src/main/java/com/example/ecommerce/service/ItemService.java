package com.example.ecommerce.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Item;
import com.example.ecommerce.repository.ItemRepository;

@Service
public class ItemService {

    private ItemRepository ItemRepository;

    @Autowired
    public ItemService(ItemRepository ItemRepository) {
        this.ItemRepository = ItemRepository;
    }

    public List<Item> getAllItems() {
        return ItemRepository.findAll();
    }

   public Item getItemById(String ItemId) {
    return ItemRepository.findById(ItemId).orElse(null);
}

    public Item createItem(Item Item) {
        return ItemRepository.save(Item);
    }

    public Item updateItem(String ItemId, Item updatedItem) {
        Optional<Item> ItemOptional = ItemRepository.findById(ItemId);

        if (ItemOptional.isPresent()) {
            Item existingItem = ItemOptional.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());
            return ItemRepository.save(existingItem);
        }

        return null;
    }

    public void deleteItem(String ItemId) {
        ItemRepository.deleteById(ItemId);
    }
}
