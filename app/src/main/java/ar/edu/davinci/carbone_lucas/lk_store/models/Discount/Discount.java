package ar.edu.davinci.carbone_lucas.lk_store.models.Discount;

public class Discount {
    private String id;
    private double percent;

    public Discount(String id, double percent) {
        this.id = id;
        this.percent = percent;
    }

    public String getId() {
        return id;
    }

    public double getPercent() {
        return percent;
    }
}