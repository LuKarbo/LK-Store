package ar.edu.davinci.carbone_lucas.lk_store.models;

public class CardItem {
    private String id;
    private String title;
    private String price;
    private int imageResId;
    private String type;

    public CardItem(String id, String title, String price, int imageResId, String type) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageResId = imageResId;
        this.type = type;
    }

    public String getId(){return id;}

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getType() {
        return type;
    }
}
