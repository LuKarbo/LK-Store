package ar.edu.davinci.carbone_lucas.lk_store.models;

public class CardItem {
    private int id;
    private String title;
    private String price;
    private int imageResId;

    public CardItem(int id, String title, String price, int imageResId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
    }

    public int getId(){return id;}

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
