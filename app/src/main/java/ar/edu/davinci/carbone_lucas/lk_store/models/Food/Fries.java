package ar.edu.davinci.carbone_lucas.lk_store.models.Food;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

public class Fries implements Product {
    private String Id;
    private String discountId;
    private boolean isDiscounted;
    private String name;
    private double price;
    private double originalePrice;
    private String img_url;

    public Fries(String Id, String discountId, boolean isDiscounted, String name, double price, double originalePrice, String img_url) {
        this.discountId = discountId;
        this.Id = Id;
        this.isDiscounted = isDiscounted;
        this.name = name;
        this.price = price;
        this.originalePrice = originalePrice;
        this.img_url = img_url;
    }

    @Override
    public String getId() {
        return this.Id;
    }

    @Override
    public String getType() {
        return "Papas Fritas";
    }

    @Override
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public void setDiscounted(boolean discounted) {
        isDiscounted = discounted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        if (isDiscounted) {
            Discount discount = DiscountController.getInstance().getDiscount(this.discountId);
            if (discount != null) {
                double discountAmount = this.price * (discount.getPercent() / 100.0);
                return this.price - discountAmount;
            }
        }
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOriginalePrice() {
        return originalePrice;
    }

    public void setOriginalePrice(int originalePrice) {
        this.originalePrice = originalePrice;
    }
}
