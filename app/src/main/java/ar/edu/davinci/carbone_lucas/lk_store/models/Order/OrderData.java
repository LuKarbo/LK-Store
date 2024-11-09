package ar.edu.davinci.carbone_lucas.lk_store.models.Order;

public class OrderData {
    private int foodAmount;
    private String foodId;
    private String foodType;

    public OrderData(int foodAmount, String foodId, String foodType) {
        this.foodAmount = foodAmount;
        this.foodId = foodId;
        this.foodType = foodType;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void cambiarCantidad(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodType() {
        return foodType;
    }

}
