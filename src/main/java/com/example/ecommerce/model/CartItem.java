package com.example.ecommerce.model;

public class CartItem {
    private String itemId;
    private int quantity;
    private double price;
    private String itemName;
    private String url;

    public CartItem(String itemId, int quantity, double price, String itemName, String url) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
        this.url = url;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}