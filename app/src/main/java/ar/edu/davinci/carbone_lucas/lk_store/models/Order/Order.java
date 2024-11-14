package ar.edu.davinci.carbone_lucas.lk_store.models.Order;

import java.util.Date;
import java.util.List;
import java.util.Iterator;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;

public class Order {
    private String id;
    private String userId;
    private Date dateTime;
    private double price;
    private List<OrderData> orderData;
    private ProductController productService;
    private MenuController menuController;

    public Order(String id, String userId, List<OrderData> orderData) {
        this.id = id;
        this.userId = userId;
        this.dateTime = new Date();
        this.orderData = orderData;
        this.productService = ProductController.getInstance();
        this.menuController = new MenuController();
        this.setPrice();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public List<OrderData> getOrderData() {
        return orderData;
    }

    private double getPriceByType(String productType, String id) {
        switch (productType.toLowerCase()) {
            case "hamburguesa":
                return productService.getHamburger(id).getPrice();
            case "papas fritas":
                return productService.getFries(id).getPrice();
            case "bebida":
                return productService.getDrink(id).getPrice();
            case "menu":
                return menuController.getMenu(id).getPrice();
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + productType);
        }
    }

    public void setPrice() {
        this.price = 0;
        if(orderData != null){
            for (OrderData item : orderData) {
                double itemPrice = getPriceByType(item.getFoodType(), item.getFoodId());
                this.price += itemPrice * item.getFoodAmount();
            }
        }
    }

    public boolean agregarItem(OrderData item) {
        boolean itemExists = false;

        for (OrderData existingItem : orderData) {
            if (existingItem.getFoodId().equalsIgnoreCase(item.getFoodId()) && existingItem.getFoodType().equalsIgnoreCase(item.getFoodType())) {

                return false;
            }
        }

        if (!itemExists) {
            this.orderData.add(item);
            double itemPrice = getPriceByType(item.getFoodType(), item.getFoodId());
            this.price += itemPrice * item.getFoodAmount();
            return true;
        }

        return false;
    }

    public void removerItemPorId(String id, String type) {
        Iterator<OrderData> iterator = this.orderData.iterator();
        while (iterator.hasNext()) {
            OrderData item = iterator.next();
            if (item.getFoodId().equals(id) && item.getFoodType().equalsIgnoreCase(type)) {
                double itemPrice = getPriceByType(item.getFoodType(), item.getFoodId());
                this.price -= itemPrice * item.getFoodAmount();
                iterator.remove();
                break;
            }
        }
    }
}