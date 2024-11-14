package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.OrderController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class ViewProductFragment extends Fragment {
    private static final String ARG_ID = "id";
    private static final String ARG_TYPE = "type";

    private String id;
    private String type;
    private ProductController productController;
    private MenuController menuController;
    private DiscountController discountController;

    public static ViewProductFragment newInstance(String id, String type) {
        ViewProductFragment fragment = new ViewProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
            type = getArguments().getString(ARG_TYPE);
            Log.i("Argumentos ID", id);
            Log.i("Argumentos type", type);
        }
        productController = ProductController.getInstance();
        menuController = new MenuController();
        discountController = DiscountController.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        ImageView productImage = view.findViewById(R.id.product_image);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productPrice = view.findViewById(R.id.product_price);
        TextView productHamburgerName = view.findViewById(R.id.product_hamburger_name);
        TextView productHamburgerPrice = view.findViewById(R.id.product_hamburger_price);
        TextView productFriesName = view.findViewById(R.id.product_fries_name);
        TextView productFriesPrice = view.findViewById(R.id.product_fries_price);
        TextView productDrinkName = view.findViewById(R.id.product_drink_name);
        TextView productDrinkPrice = view.findViewById(R.id.product_drink_price);
        TextView productDiscountLabel = view.findViewById(R.id.product_discount_label);
        TextView productDiscountPercent = view.findViewById(R.id.product_discount_percent);

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        Button addToCartButton = view.findViewById(R.id.button_add_to_cart);
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Agregado al carrito", Toast.LENGTH_SHORT).show();
            OrderController oc = OrderController.getInstance(User.getInstance().getUserId());
            oc.addItem(type,id,1);
        });

        switch (type) {
            case "Hamburguesa":
                Hamburger hamburger = productController.getHamburger(id);
                if (hamburger != null) {
                    productImage.setImageResource(R.drawable.hambur_2);
                    productName.setText(hamburger.getName());
                    productPrice.setText(String.format("%.2f", hamburger.getPrice()));
                    if (hamburger.isDiscounted()) {
                        Discount discount = discountController.getDiscount(hamburger.getDiscountId());
                        if (discount != null) {
                            productDiscountLabel.setVisibility(View.VISIBLE);
                            productDiscountPercent.setVisibility(View.VISIBLE);
                            productDiscountLabel.setText("Descuento");
                            productDiscountPercent.setText(String.format("%.2f%%", discount.getPercent()));
                        }
                    } else {
                        productDiscountLabel.setVisibility(View.GONE);
                        productDiscountPercent.setVisibility(View.GONE);
                    }
                }
                break;
            case "Papas Fritas":
                Fries fries = productController.getFries(id);
                if (fries != null) {
                    productImage.setImageResource(R.drawable.hambur_1);
                    productName.setText(fries.getName());
                    productPrice.setText(String.format("%.2f", fries.getPrice()));
                    if (fries.isDiscounted()) {
                        Discount discount = discountController.getDiscount(fries.getDiscountId());
                        if (discount != null) {
                            productDiscountLabel.setVisibility(View.VISIBLE);
                            productDiscountPercent.setVisibility(View.VISIBLE);
                            productDiscountLabel.setText("Descuento");
                            productDiscountPercent.setText(String.format("%.2f%%", discount.getPercent()));
                        }
                    } else {
                        productDiscountLabel.setVisibility(View.GONE);
                        productDiscountPercent.setVisibility(View.GONE);
                    }
                }
                break;
            case "Bebida":
                Drink drink = productController.getDrink(id);
                if (drink != null) {

                    productImage.setImageResource(R.drawable.hambur_3);
                    productName.setText(drink.getName());
                    productPrice.setText(String.format("%.2f", drink.getPrice()));

                }
                break;
            case "Menu":
                MenuData menu = menuController.getMenu(id);
                if (menu != null) {
                    productImage.setImageResource(R.drawable.hambur_2);
                    productName.setText("Menu [" + menu.getId() + "]");
                    productPrice.setText(String.format("%.2f", menu.getPrice()));
                    productHamburgerName.setText(menu.getHamburger().getName());
                    productHamburgerPrice.setText(String.format("%.2f", menu.getHamburger().getPrice()));
                    productFriesName.setText(menu.getFries().getName());
                    productFriesPrice.setText(String.format("%.2f", menu.getFries().getPrice()));
                    productDrinkName.setText(menu.getDrink().getName());
                    productDrinkPrice.setText(String.format("%.2f", menu.getDrink().getPrice()));
                    view.findViewById(R.id.menu_details_layout).setVisibility(View.VISIBLE);

                    if (menu.isDiscounted()) {
                        Discount discount = discountController.getDiscount(menu.getDiscount().getId());
                        if (discount != null) {
                            productDiscountLabel.setVisibility(View.VISIBLE);
                            productDiscountPercent.setVisibility(View.VISIBLE);
                            productDiscountLabel.setText("Descuento");
                            productDiscountPercent.setText(String.format("%.2f%%", discount.getPercent()));
                        }
                    } else {
                        productDiscountLabel.setVisibility(View.GONE);
                        productDiscountPercent.setVisibility(View.GONE);
                    }
                }
                break;
        }

        return view;
    }
}