package ar.edu.davinci.carbone_lucas.lk_store.models;

public class CardItem {
    private String id;
    private String title;
    private String price;
    private String type;
    private String img_url;

    public CardItem(String id, String title, String price, String img_url, String type) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.img_url = img_url;
    }

    public String getId(){return id;}

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getImg_url() {
        return img_url;
    }
}
