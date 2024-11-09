package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.CartAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.R;

import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.OrderController;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class ShopCartFragment extends Fragment implements CartAdapter.CartAdapterListener {
    private OrderController orderController;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceText;
    private Button confirmButton;

    public static ShopCartFragment newInstance() {
        return new ShopCartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        orderController = OrderController.getInstance(User.getInstance().getUserId());
        recyclerView = view.findViewById(R.id.cart_recycler_view);
        totalPriceText = view.findViewById(R.id.total_price_text);
        confirmButton = view.findViewById(R.id.confirm_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (orderController != null) {
            cartAdapter = new CartAdapter(orderController, this);
            recyclerView.setAdapter(cartAdapter);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderController != null && !orderController.isOrderEmpty()) {
                    if(User.getInstance().getAddress() != null && !User.getInstance().getAddress().isEmpty()){
                        Toast.makeText(getContext(), "Orden confirmada", Toast.LENGTH_SHORT).show();
                        orderController.clearOrder();
                        updateUI();
                    }
                    else{
                        Toast.makeText(getContext(), "Vaya a su cuenta y Agregue su dirección", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "El carrito está vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateUI();

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    public void setOrderController(OrderController controller) {
        this.orderController = controller;
        updateUI();
    }

    @Override
    public void onItemChanged() {
        updateUI();
    }

    private void updateUI() {
        if (orderController != null) {
            if (cartAdapter == null) {
                cartAdapter = new CartAdapter(orderController, this);
                recyclerView.setAdapter(cartAdapter);
            }
            cartAdapter.updateItems(orderController.getOrderItems());
            totalPriceText.setText(String.format("Total: $%.2f", orderController.getOrderTotal()));
        }
    }
}