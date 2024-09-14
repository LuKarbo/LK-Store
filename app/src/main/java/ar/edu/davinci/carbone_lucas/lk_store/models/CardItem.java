package ar.edu.davinci.carbone_lucas.lk_store.models;

public class CardItem {
    private String title;
    private String price;
    private int imageResId;

    public CardItem(String title, String price, int imageResId) {
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
