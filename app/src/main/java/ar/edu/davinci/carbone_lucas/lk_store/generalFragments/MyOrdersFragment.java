package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.OrdersAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.OrderController;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class MyOrdersFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private OrdersAdapter adapter;
    private String userId;
    private ImageButton backButton;
    private TextView toolbarTitle;

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupRecyclerView();
        setupToolbar();
        loadOrders();
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.ordersRecyclerView);
        emptyView = view.findViewById(R.id.emptyOrdersView);
        backButton = view.findViewById(R.id.back_button);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        userId = User.getInstance().getUserId();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }

    private void setupToolbar() {
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
    }

    private void loadOrders() {
        OrderController orderController = OrderController.getInstance(userId);
        List<Order> orders = orderController.getMyOrders(userId);

        updateUI(orders);
    }

    private void updateUI(List<Order> orders) {
        if (orders.isEmpty()) {
            showEmptyState();
        } else {
            showOrders(orders);
        }
    }

    private void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void showOrders(List<Order> orders) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        adapter = new OrdersAdapter(orders);
        recyclerView.setAdapter(adapter);
    }
}