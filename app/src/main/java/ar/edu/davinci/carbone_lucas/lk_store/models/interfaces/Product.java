package ar.edu.davinci.carbone_lucas.lk_store.models.interfaces;

public interface Product {
    String getDiscountId();
    boolean isDiscounted();
    String getName();
    double getPrice();
    String getImg_url();
    String getId();
}
