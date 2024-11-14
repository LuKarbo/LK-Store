package ar.edu.davinci.carbone_lucas.lk_store.models.Discount;

public class Discount {
    private String id;
    private double percent;
    private boolean isActive;

    public Discount(String id, double percent, boolean isActive) {
        this.id = id;
        this.percent = percent;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public double getPercent() {
        return percent;
    }

    public boolean isActive(){
        return this.isActive;
    }
}