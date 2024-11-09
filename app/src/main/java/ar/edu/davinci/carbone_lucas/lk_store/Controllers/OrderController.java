package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;

public class OrderController {
    private Order currentOrder;

    public OrderController(String userId) {
        this.currentOrder = new Order(
                UUID.randomUUID().toString(),
                userId,
                new ArrayList<>()
        );
    }

    public boolean addItem(String foodType, String foodId, int amount) {
        OrderData newItem = new OrderData(amount, foodId, foodType);
        currentOrder.agregarItem(newItem);
        return true;
    }

    public boolean removeItem(String foodId) {
        currentOrder.removerItemPorId(foodId);
        return true;
    }

    public boolean updateItemAmount(String foodId, int newAmount) {
        List<OrderData> items = currentOrder.getOrderData();
        for (OrderData item : items) {
            if (item.getFoodId().equals(foodId)) {
                item.cambiarCantidad(newAmount);
                currentOrder.setPrice();
                return true;
            }
        }
        return false;
    }

    public List<OrderData> getOrderItems() {
        return currentOrder.getOrderData();
    }

    public double getOrderTotal() {
        return currentOrder.getPrice();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void clearOrder() {
        this.currentOrder = new Order(
                UUID.randomUUID().toString(),
                currentOrder.getUserId(),
                new ArrayList<>()
        );
    }

    public boolean isOrderEmpty() {
        return currentOrder.getOrderData().isEmpty();
    }
}