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
    private static List<Order> simulatedOrders;

    private OrderController(String userId) {
        this.userId = userId;
        initializeSimulatedOrders();
    }

    private void initializeSimulatedOrders() {
        simulatedOrders = new ArrayList<>();

        List<OrderData> items1 = new ArrayList<>();
        items1.add(new OrderData(2, "1", "hamburguesa"));
        items1.add(new OrderData(1, "1", "bebida"));
        Order order1 = new Order(UUID.randomUUID().toString(), "234", items1);

        List<OrderData> items2 = new ArrayList<>();
        items2.add(new OrderData(3, "1", "papas fritas"));
        items2.add(new OrderData(1, "1", "menu"));
        Order order2 = new Order(UUID.randomUUID().toString(), "234", items2);

        List<OrderData> items3 = new ArrayList<>();
        items3.add(new OrderData(1, "2", "hamburguesa"));
        items3.add(new OrderData(2, "2", "menu"));
        Order order3 = new Order(UUID.randomUUID().toString(), this.userId, items3);

        List<OrderData> items4 = new ArrayList<>();
        items4.add(new OrderData(2, "3", "hamburguesa"));
        items4.add(new OrderData(1, "2", "papas fritas"));
        items4.add(new OrderData(3, "1", "bebida"));
        items4.add(new OrderData(1, "2", "menu"));
        Order order4 = new Order(UUID.randomUUID().toString(), this.userId, items4);

        List<OrderData> items5 = new ArrayList<>();
        items5.add(new OrderData(4, "3", "bebida"));
        items5.add(new OrderData(2, "4", "papas fritas"));
        Order order5 = new Order(UUID.randomUUID().toString(), this.userId, items5);

        simulatedOrders.add(order1);
        simulatedOrders.add(order2);
        simulatedOrders.add(order3);
        simulatedOrders.add(order4);
        simulatedOrders.add(order5);
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
    public List<Order> getMyOrders(String userId) {
        List<Order> myOrders = new ArrayList<>();
        for (Order order : simulatedOrders) {
            if (order.getUserId().equals(userId)) {
                myOrders.add(order);
            }
        }
        return myOrders;
    }

    public List<Order> getAllOrders() {
        if (User.getInstance().isAdmin()) {
            return new ArrayList<>(simulatedOrders);
        } else {
            return new ArrayList<>();
        }
    }

}