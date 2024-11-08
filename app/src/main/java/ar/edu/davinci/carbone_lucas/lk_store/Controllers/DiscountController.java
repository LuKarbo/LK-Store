package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import java.util.Arrays;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;

public class DiscountController {
    public Discount getDiscount(String discountId) {
        return new Discount("6c0QV5FEsZPCczP7VZpK", 13.40);

    }

    public List<String> getDiscountIds() {

        return Arrays.asList("6c0QV5FEsZPCczP7VZpK");
    }
}
