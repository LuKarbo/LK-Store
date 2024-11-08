package ar.edu.davinci.carbone_lucas.lk_store;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;
import ar.edu.davinci.carbone_lucas.lk_store.models.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class ViewProductFragment extends Fragment {
    private static final String ARG_ID = "id";
    private static final String ARG_TYPE = "type";

    private String id;
    private String type;
    private ProductController productController;
    private MenuController menuController;

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
        }
        productController = new ProductController();
        menuController = new MenuController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container, false);

        ImageView productImage = view.findViewById(R.id.product_image);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productPrice = view.findViewById(R.id.product_price);

        switch (type) {
            case "Hamburguesa":
                Hamburger hamburger = productController.getHamburger(id);
                if (hamburger != null) {

                    productImage.setImageResource(R.drawable.hambur_2);
                    productName.setText(hamburger.getName());
                    productPrice.setText(String.format("%.2f", hamburger.getPrice()));
                }
                break;
            case "Papas Fritas":
                Fries fries = productController.getFries(id);
                if (fries != null) {

                    productImage.setImageResource(R.drawable.hambur_1);
                    productName.setText(fries.getName());
                    productPrice.setText(String.format("%.2f", fries.getPrice()));

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
                    productName.setText(menu.getHamburger().getName() + ", " + menu.getFries().getName() + ", " + menu.getDrink().getName());
                    productPrice.setText(String.format("%.2f", menu.getPrice()));
                }
                break;
        }

        return view;
    }
}