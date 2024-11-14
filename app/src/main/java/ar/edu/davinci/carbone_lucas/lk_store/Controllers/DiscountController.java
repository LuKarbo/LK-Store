package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.DiscountAPIGetAll;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;

public class DiscountController {
    private static DiscountController instance;
    private List<Discount> discountList;

    private DiscountController() {
        loadData();
    }

    public static DiscountController getInstance() {
        if (instance == null) {
            instance = new DiscountController();
        }
        return instance;
    }

    private void loadData() {
        DiscountAPIGetAll task = new DiscountAPIGetAll();
        try {
            discountList = task.execute().get();
        } catch (Exception e) {
            Log.e("DiscountController", "Error loading discount data: " + e.getMessage());
        }
    }

    public Discount getDiscount(String discountId) {
        for (Discount discount : discountList) {
            if (discount.getId().equals(discountId)) {
                return discount;
            }
        }
        return null;
    }

    public List<Discount> getAllDiscounts() {
        return discountList;
    }
}