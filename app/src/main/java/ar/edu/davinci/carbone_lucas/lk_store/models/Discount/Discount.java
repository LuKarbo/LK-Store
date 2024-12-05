package ar.edu.davinci.carbone_lucas.lk_store.models.Discount;

public class Discount {
    private String id;
    private String code;
    private double percent;
    private boolean isActive;

    public Discount(String id, String code, double percent, boolean isActive) {
        this.id = id;
        this.code = code;
        this.percent = percent;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public double getPercent() {
        return percent;
    }

    public boolean isActive(){
        return this.isActive;
    }
}