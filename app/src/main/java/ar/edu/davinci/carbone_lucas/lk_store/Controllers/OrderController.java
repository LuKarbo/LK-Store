package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetOrdersApi;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;

public class OrderController {
    private Order currentOrder;
    private String userId;
    private static OrderController instance;
    private static List<Order> orders;

    private OrderController(String userId) {
        this.userId = userId;
        this.orders = getOrders();
    }

    public static synchronized OrderController getInstance(String userId) {
        if (instance == null) {
            instance = new OrderController(userId);
        }
        return instance;
    }

    private void initializeOrderIfNeeded() {
        if (currentOrder == null) {
            this.currentOrder = new Order(
                    UUID.randomUUID().toString(),
                    userId,
                    new ArrayList<>()
            );
        }
    }

    public boolean addItem(String foodType, String foodId, int amount) {
        initializeOrderIfNeeded();
        OrderData newItem = new OrderData(amount, foodId, foodType);
        boolean result = currentOrder.agregarItem(newItem);
        if(!result) {
            aumentarItemAmount(foodId,foodType);
        }
        return true;
    }

    public boolean removeItem(String foodId, String type) {
        initializeOrderIfNeeded();
        currentOrder.removerItemPorId(foodId,type);
        return true;
    }

    public boolean updateItemAmount(String foodId, String type, int newAmount) {
        initializeOrderIfNeeded();
        List<OrderData> items = currentOrder.getOrderData();
        for (OrderData item : items) {
            if (item.getFoodId().equals(foodId) && item.getFoodType().equalsIgnoreCase(type)) {
                item.cambiarCantidad(newAmount);
                currentOrder.setPrice();
                return true;
            }
        }
        return false;
    }

    public void aumentarItemAmount(String foodId, String type){
        initializeOrderIfNeeded();
        List<OrderData> items = currentOrder.getOrderData();
        for (OrderData item : items) {
            if (item.getFoodId().equals(foodId) && item.getFoodType().equalsIgnoreCase(type)) {
                item.aumentarCantidadPorCopia();
                currentOrder.setPrice();
                break;
            }
        }
    }

    public List<OrderData> getOrderItems() {
        initializeOrderIfNeeded();
        return currentOrder.getOrderData();
    }

    public double getOrderTotal() {
        initializeOrderIfNeeded();
        return currentOrder.getPrice();
    }

    public Order getCurrentOrder() {
        initializeOrderIfNeeded();
        return currentOrder;
    }

    public void clearOrder() {
        this.currentOrder = new Order(
                UUID.randomUUID().toString(),
                userId,
                new ArrayList<>()
        );
    }

    public boolean isOrderEmpty() {
        initializeOrderIfNeeded();
        return currentOrder.getOrderData().isEmpty();
    }

    public List<Order> getMyOrders(String userId) {
        List<Order> myOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                myOrders.add(order);
            }
        }
        return myOrders;
    }

    private List<Order> getOrders() {
        GetOrdersApi getOrdersApi = new GetOrdersApi();
        try {
            orders = getOrdersApi.execute().get();
        } catch (Exception e) {
            Log.e("DiscountController", "Error loading discount data: " + e.getMessage());
        }
        return orders;
    }
}