package com.baeldung.rocketmq.event;

public class CartItemEvent {
    private int id;
    private String itemId;
    private int quantity;

    public CartItemEvent() {
    }

    public CartItemEvent(int id, String itemId, int quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CartItemEvent{" + "id=" + id + ", itemId='" + itemId + '\'' + ", quantity=" + quantity + '}';
    }
}
