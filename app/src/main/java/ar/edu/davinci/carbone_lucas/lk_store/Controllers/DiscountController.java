package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.DiscountAPIGetAll;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class DiscountController {
    private static DiscountController instance;
    private List<Discount> discountList;
    private FirebaseFirestore db;
    private static final String COLLECTION_NAME = "discount";

    private DiscountController() {
        db = FirebaseFirestore.getInstance();
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

    public interface OnDiscountsLoadedListener {
        void onDiscountsLoaded(List<Discount> discounts);
        void onError(Exception e);
    }

    public void getAllFireStoreDiscounts(OnDiscountsLoadedListener listener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    try {
                        List<Discount> discountList = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> discountData = documentSnapshot.getData();
                            String documentId = documentSnapshot.getId();

                            String code = (String) discountData.get("code");

                            double porcent = 0.0;
                            Object porcentObject = discountData.get("porcent");
                            if (porcentObject instanceof Long) {
                                porcent = ((Long) porcentObject).doubleValue();
                            } else if (porcentObject instanceof Double) {
                                porcent = (Double) porcentObject;
                            }

                            boolean isActive = (boolean) discountData.get("isActive");

                            Discount discount = new Discount(documentId, code, porcent, isActive);
                            discountList.add(discount);
                        }
                        listener.onDiscountsLoaded(discountList);
                    } catch (Exception e) {
                        Log.e("DiscountController", "Error al mapear los datos a Discount: " + e.getMessage());
                        listener.onError(e);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("DiscountController", "Error al consultar: " + e.getMessage());
                    listener.onError(e);
                });

    }



    private boolean isDiscountInUse(String discountId) {
        MenuController menuController = MenuController.getInstance();
        List<MenuData> allMenus = menuController.getAllMenus();

        for (MenuData menu : allMenus) {
            if (menu.getDiscount() != null &&
                    menu.getDiscount().getId().equals(discountId) &&
                    menu.isDiscounted()) {
                return true;
            }
        }

        ProductController productController = ProductController.getInstance();

        List<Hamburger> hamburgers = productController.getHamburgers();
        for (Hamburger hamburger : hamburgers) {
            if (hamburger.isDiscounted() &&
                    hamburger.getDiscountId() != null &&
                    hamburger.getDiscountId().equals(discountId)) {
                return true;
            }
        }

        List<Fries> fries = productController.getFries();
        for (Fries fry : fries) {
            if (fry.isDiscounted() &&
                    fry.getDiscountId() != null &&
                    fry.getDiscountId().equals(discountId)) {
                return true;
            }
        }

        List<Drink> drinks = productController.getDrinks();
        for (Drink drink : drinks) {
            if (drink.isDiscounted() &&
                    drink.getDiscountId() != null &&
                    drink.getDiscountId().equals(discountId)) {
                return true;
            }
        }

        return false;
    }

    public void deleteDiscount(String discountId, OnDiscountOperationListener listener) {
        if (isDiscountInUse(discountId)) {
            if (listener != null) {
                listener.onFailure(new Exception("El codigo se encuentra en uso"));
            }
            return;
        }

        db.collection(COLLECTION_NAME).document(discountId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    discountList.removeIf(discount -> discount.getId().equals(discountId));
                    if (listener != null) listener.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e);
                });
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

    public void createDiscount(String code, double percent, boolean isActive, OnDiscountOperationListener listener) {
        String newDiscountId = db.collection(COLLECTION_NAME).document().getId();

        Discount newDiscount = new Discount(newDiscountId,code, percent, isActive);

        Map<String, Object> discountData = new HashMap<>();
        discountData.put("code", code);
        discountData.put("porcent", percent);
        discountData.put("isActive", isActive);

        db.collection(COLLECTION_NAME).document(newDiscountId)
                .set(discountData)
                .addOnSuccessListener(aVoid -> {
                    discountList.add(newDiscount);
                    if (listener != null) listener.onSuccess(newDiscount);
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e);
                });
    }

    public void updateDiscount(String discountId, String code, Double percent, Boolean isActive, OnDiscountOperationListener listener) {
        Map<String, Object> updates = new HashMap<>();

        if (code != null) updates.put("code", code);
        if (percent != null) updates.put("porcent", percent);
        if (isActive != null) updates.put("isActive", isActive);

        db.collection(COLLECTION_NAME).document(discountId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    for (int i = 0; i < discountList.size(); i++) {
                        Discount discount = discountList.get(i);
                        if (discount.getId().equals(discountId)) {
                            Discount updatedDiscount = new Discount(
                                    discountId,
                                    code,
                                    percent != null ? percent : discount.getPercent(),
                                    isActive != null ? isActive : discount.isActive()
                            );
                            discountList.set(i, updatedDiscount);
                            break;
                        }
                    }

                    if (listener != null) listener.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e);
                });
    }

    public void updateDiscountStatus(String discountId, Boolean isActive, OnDiscountOperationListener listener) {
        if (discountId == null) {
            Log.e("DiscountController", "El ID del descuento es null.");
            if (listener != null) listener.onFailure(new Exception("El ID del descuento no puede ser null"));
            return;
        }

        Map<String, Object> updates = new HashMap<>();
        if (isActive != null) {
            updates.put("isActive", isActive);
        }

        db.collection(COLLECTION_NAME).document(discountId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    for (int i = 0; i < discountList.size(); i++) {
                        Discount discount = discountList.get(i);
                        if (discount.getId().equals(discountId)) {
                            Discount updatedDiscount = new Discount(
                                    discountId,
                                    discount.getCode(),
                                    discount.getPercent(),
                                    isActive != null ? isActive : discount.isActive()
                            );
                            discountList.set(i, updatedDiscount);
                            break;
                        }
                    }
                    if (listener != null) listener.onSuccess(null);
                })
                .addOnFailureListener(e -> {
                    Log.e("DiscountController", "Error al actualizar el descuento: " + e.getMessage());
                    if (listener != null) listener.onFailure(e);
                });
    }


    public interface OnDiscountOperationListener {
        void onSuccess(Discount discount);
        void onFailure(Exception e);
    }

}