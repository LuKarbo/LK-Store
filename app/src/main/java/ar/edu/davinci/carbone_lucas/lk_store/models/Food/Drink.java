package ar.edu.davinci.carbone_lucas.lk_store.models.Food;

import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

public class Drink implements Product {
    private String Id;
    private String name;
    private double price;
    private double originalePrice;
    private String img_url;

    public Drink(String Id, String name, double price, double originalePrice, String img_url) {
        this.Id = Id;
        this.name = name;
        this.price = price;
        this.originalePrice = originalePrice;
        this.img_url = img_url;
    }

    @Override
    public String getDiscountId() {
        return "";
    }

    @Override
    public boolean isDiscounted() {
        return false;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public String getType() {
        return "Bebida";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalePrice() {
        return originalePrice;
    }

    public void setOriginalePrice(int originalePrice) {
        this.originalePrice = originalePrice;
    }
}
