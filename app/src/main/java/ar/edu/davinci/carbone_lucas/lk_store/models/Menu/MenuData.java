package ar.edu.davinci.carbone_lucas.lk_store.models.Menu;

import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;

public class MenuData {
    private String id;
    private Hamburger hamburger;
    private Fries fries;
    private Drink drink;
    private Discount discount;
    private boolean isDiscounted;
    private double price;
    private String img_url;

    public MenuData(String id, Hamburger hamburger, Fries fries, Drink drink, Discount discount, boolean isDiscounted, double price, String img_url) {
        this.id = id;
        this.hamburger = hamburger;
        this.fries = fries;
        this.drink = drink;
        this.discount = discount;
        this.isDiscounted = isDiscounted;
        this.price = price;
        this.img_url = img_url;
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

    public String getImg_url() {return img_url; }

    public double getPrice() {
        if (isDiscounted && discount != null) {
            double discountAmount = price * (discount.getPercent() / 100.0);
            return price - discountAmount;
        }
        return price;
    }
}
