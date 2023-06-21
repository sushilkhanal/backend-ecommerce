package com.example.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Item;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ItemService;

@CrossOrigin
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;

    @Autowired
    public CartController(CartService cartService, ItemService itemService) {
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();

        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(carts);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> viewCart(@PathVariable String userId) {
        Optional<Cart> optionalCart = cartService.getCartByUserId(userId);

        if (optionalCart.isPresent()) {
            Cart userCart = optionalCart.get();
            int totalItems = userCart.getTotalItems();
            List<CartItem> cartItems = userCart.getCartItems();

            Map<String, Object> response = new HashMap<>();
            response.put("cartId", userCart.getId());
            response.put("totalItems", totalItems);
            response.put("items", cartItems);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Cart> addToCart(@PathVariable String userId, @RequestBody CartItem cartItem) {
        Optional<Cart> existingCart = cartService.getCartByUserId(userId);

        if (existingCart.isPresent()) {
            Cart userCart = existingCart.get();

            Optional<CartItem> existingCartItem = userCart.getCartItems().stream()
                    .filter(item -> item.getItemId().equals(cartItem.getItemId()))
                    .findFirst();

            if (existingCartItem.isPresent()) {
                CartItem cartItemToUpdate = existingCartItem.get();
                int newQuantity = cartItemToUpdate.getQuantity() + cartItem.getQuantity();
                cartItemToUpdate.setQuantity(newQuantity);
            } else {
                Item item = itemService.getItemById(cartItem.getItemId());
                if (item == null) {
                    return ResponseEntity.badRequest().build();
                }

                cartItem.setPrice(item.getPrice());
                cartItem.setItemName(item.getName());
                cartItem.setUrl(item.getUrl());

                userCart.getCartItems().add(cartItem);
            }

            Cart updatedCart = cartService.updateCart(userCart);
            return ResponseEntity.ok(updatedCart);
        } else {
            Item item = itemService.getItemById(cartItem.getItemId());
            if (item == null) {
                return ResponseEntity.badRequest().build();
            }

            cartItem.setPrice(item.getPrice());
            cartItem.setItemName(item.getName());
            cartItem.setUrl(item.getUrl());

            Cart newCart = new Cart(userId);
            newCart.getCartItems().add(cartItem);

            Cart createdCart = cartService.createCart(newCart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable String id, @RequestBody Cart cart) {
        Optional<Cart> existingCart = cartService.getCartById(id);
        if (existingCart.isPresent()) {
            cart.setId(id);
            Cart updatedCart = cartService.updateCart(cart);
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable String userId, @PathVariable String itemId) {
        Optional<Cart> existingCart = cartService.getCartByUserId(userId);

        if (existingCart.isPresent()) {
            Cart userCart = existingCart.get();

            Optional<CartItem> cartItemToDelete = userCart.getCartItems().stream()
                    .filter(item -> item.getItemId().equals(itemId))
                    .findFirst();

            if (cartItemToDelete.isPresent()) {
                userCart.getCartItems().remove(cartItemToDelete.get());
                cartService.updateCart(userCart);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable String id) {
        boolean deleted = cartService.deleteCart(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}