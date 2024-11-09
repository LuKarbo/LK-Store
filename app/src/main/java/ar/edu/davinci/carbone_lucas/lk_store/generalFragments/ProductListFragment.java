package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.ProductListAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;
import ar.edu.davinci.carbone_lucas.lk_store.MainActivity;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

public class ProductListFragment extends Fragment {
    private static final String ARG_PRODUCT_TYPE = "product_type";

    private String productType;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private ProductListAdapter adapter;
    private List<? extends Product> productList;
    private String categoria;

    public static ProductListFragment newInstance(String productType) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_TYPE, productType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productType = getArguments().getString(ARG_PRODUCT_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        TextView titleTextView = view.findViewById(R.id.product_title);
        titleTextView.setText(String.format(Locale.getDefault(), "Categoría: %s", productType));

        searchEditText = view.findViewById(R.id.product_search);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Recuperar la lista de productos según el tipo de producto
        productList = getProductList(productType);
        adapter = new ProductListAdapter(productList, (product) -> {
            ViewProductFragment viewProductFragment = ViewProductFragment.newInstance(product.getId(), product.getType());
            ((MainActivity) requireActivity()).replaceFragment(viewProductFragment);
        }, (product) -> {
            Toast.makeText(getContext(), "Agregar al carrito: " + product.getId() + " | " + product.getType(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void filterProducts(String query) {
        if (productList != null) {
            List<? extends Product> filteredList = productList.stream()
                    .filter(product -> product.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            adapter.updateProductList(filteredList);
        }
    }

    private List<? extends Product> getProductList(String productType) {
        ProductController productService = new ProductController();
        switch (productType) {
            case "Hamburguesas":
                return productService.getHamburgers();
            case "Papas Fritas":
                return productService.getFries();
            case "Bebidas":
                return productService.getDrinks();
            default:
                return new ArrayList<>();
        }
    }
}