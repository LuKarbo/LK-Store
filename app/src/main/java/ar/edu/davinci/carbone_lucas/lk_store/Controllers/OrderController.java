package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class OrderController {
    private Order currentOrder;
    private String userId;
    private static OrderController instance;

    private OrderController(String userId) {
        this.userId = userId;
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

    // ---------------------- TERMINAR ----------------------
    public List<Order> getMyOrders(String userId){
        return new ArrayList<>();
    }

    public List<Order> getAllOrders(){
        if(User.getInstance().isAdmin()){
            return new ArrayList<>();
        }
        else{
            return new ArrayList<>();
        }

    }

}