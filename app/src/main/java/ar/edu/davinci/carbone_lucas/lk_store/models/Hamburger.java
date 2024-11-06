package ar.edu.davinci.carbone_lucas.lk_store.models;


import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

public class Hamburger implements Product {
    private String Id;
    private String discountId;
    private boolean isDiscounted;
    private String name;
    private double price;
    private int stock;
    private String img_url;

    public Hamburger(String Id, String discountId, boolean isDiscounted, String name, double price, int stock, String img_url) {
        this.Id = Id;
        this.discountId = discountId;
        this.isDiscounted = isDiscounted;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.img_url = img_url;
    }

    @Override
    public String getImg_url() {
        return img_url;
    }

    @Override
    public String getId() {
        return this.Id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public void setDiscounted(boolean discounted) {
        isDiscounted = discounted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
