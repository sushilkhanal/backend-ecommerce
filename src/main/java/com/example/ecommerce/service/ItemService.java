package com.example.ecommerce.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Item;
import com.example.ecommerce.repository.ItemRepository;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

   public Item getItemById(String itemId) {
    return itemRepository.findById(itemId).orElse(null);
}

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(String itemId, Item updatedItem) {
        Optional<Item> ItemOptional = itemRepository.findById(itemId);

        if (ItemOptional.isPresent()) {
            Item existingItem = ItemOptional.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());
            return itemRepository.save(existingItem);
        }

        return null;
    }

    public void deleteItem(String itemId) {
        itemRepository.deleteById(itemId);
    }
}
