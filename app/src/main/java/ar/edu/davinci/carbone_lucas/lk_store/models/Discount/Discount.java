package ar.edu.davinci.carbone_lucas.lk_store.models.Discount;

public class Discount {
    private String id;
    private String code;
    private double porcent;
    private boolean isActive;

    public Discount() {
    }

    public Discount(String id, String code, double porcent, boolean isActive) {
        this.id = id;
        this.code = code;
        this.porcent = porcent;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public double getPercent() {
        return porcent;
    }

    public boolean isActive(){
        return this.isActive;
    }
}