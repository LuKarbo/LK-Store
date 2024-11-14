package ar.edu.davinci.carbone_lucas.lk_store.models.Order;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;

public class OrderData {
    private int foodAmount;
    private String foodId;
    private String foodType;
    private ProductController productService;
    private MenuController menuController;

    public OrderData(int foodAmount, String foodId, String foodType) {
        this.foodAmount = foodAmount;
        this.foodId = foodId;
        this.foodType = foodType;
        this.productService = ProductController.getInstance();
        this.menuController = new MenuController();
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void cambiarCantidad(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public void aumentarCantidadPorCopia(){
        this.foodAmount += 1;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getName(){

        switch (foodType.toLowerCase()) {
            case "hamburguesa":
                return productService.getHamburger(foodId).getName();
            case "papas fritas":
                return productService.getFries(foodId).getName();
            case "bebida":
                return productService.getDrink(foodId).getName();
            case "menu":
                return "Menu #" + foodId;
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + foodType);
        }
    }

}
