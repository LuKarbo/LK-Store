package ar.edu.davinci.carbone_lucas.lk_store.models.Menu;

public class Menu {
    private String id;
    private String hamburgerId;
    private String friesId;
    private String drinkId;
    private boolean isDiscounted;
    private String discountId;
    private double price;
    private String img_url;

    public Menu(String id, String hamburgerId, String friesId, String drinkId, String discountId, boolean isDiscounted, double price, String img_url) {
        this.id = id;
        this.discountId = discountId;
        this.hamburgerId = hamburgerId;
        this.friesId = friesId;
        this.drinkId = drinkId;
        this.isDiscounted = isDiscounted;
        this.price = price;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public String getDiscountId() {
        return discountId;
    }

    public String getHamburgerId() {
        return hamburgerId;
    }

    public String getFriesId() {
        return friesId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getImg_url(){ return img_url; }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public double getPrice() {
        return price;
    }
}
