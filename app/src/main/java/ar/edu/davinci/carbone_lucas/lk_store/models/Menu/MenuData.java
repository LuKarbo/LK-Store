package ar.edu.davinci.carbone_lucas.lk_store.models.Menu;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Hamburger;

public class MenuData {
    private String id;
    private Hamburger hamburger;
    private Fries fries;
    private Drink drink;
    private Discount discount;
    private boolean isDiscounted;
    private double price;

    public MenuData(String id, Hamburger hamburger, Fries fries, Drink drink, Discount discount, boolean isDiscounted, double price) {
        this.id = id;
        this.hamburger = hamburger;
        this.fries = fries;
        this.drink = drink;
        this.discount = discount;
        this.isDiscounted = isDiscounted;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Hamburger getHamburger() {
        return hamburger;
    }

    public Fries getFries() {
        return fries;
    }

    public Drink getDrink() {
        return drink;
    }

    public Discount getDiscount() {
        return discount;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public String getType(){ return "Menu"; }

    public double getPrice() {
        if (isDiscounted) {
            Discount discount = this.discount;
            if (discount != null) {
                double discountAmount = this.price * (discount.getPercent() / 100.0);
                return this.price - discountAmount;
            }
        }
        return this.price;
    }
}
