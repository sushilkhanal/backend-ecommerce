package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Item;
import com.example.ecommerce.service.ItemService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllitems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public Item getitemById(@PathVariable String itemId) {
        return itemService.getItemById(itemId);
    }

    @PostMapping
    public Item createitem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{itemId}")
    public Item updateitem(@PathVariable String itemId, @RequestBody Item item) {
        return itemService.updateItem(itemId, item);
    }

    @DeleteMapping("/{itemId}")
    public void deleteitem(@PathVariable String itemId) {
        itemService.deleteItem(itemId);
    }
}
