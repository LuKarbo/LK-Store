package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.DiscountAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Adapters.DiscountCreationDialog;
import ar.edu.davinci.carbone_lucas.lk_store.Adapters.SupportAdminAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Adapters.UserAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.UserController;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.SupportController;
import ar.edu.davinci.carbone_lucas.lk_store.models.UserDTO;

public class AdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private SupportController supportController;
    private SupportAdminAdapter supportAdapter;
    private View view;

    public static AdminFragment newInstance() {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        tabLayout = view.findViewById(R.id.tabLayout);

        supportController = SupportController.getInstance();

        setupTabs();
        setupRecyclerView();

        return view;
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Consultas"));
        tabLayout.addTab(tabLayout.newTab().setText("Usuarios"));
        tabLayout.addTab(tabLayout.newTab().setText("Descuentos"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showSupports();
                        break;
                    case 1:
                        showUsers();
                        break;
                    case 2:
                        showDiscounts();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showSupports();
    }

    private void showSupports() {
        List<Support> supports = supportController.getConsultas();
        supportAdapter = new SupportAdminAdapter(
                supports,
                (support, response) -> {
                    Toast.makeText(getContext(), "Respuesta enviada", Toast.LENGTH_SHORT).show();
                },
                supportController
        );
        recyclerView.setAdapter(supportAdapter);
    }

    private void showUsers() {
        UserController userController = new UserController();
        List<UserDTO> users = userController.getAllUsers();
        UserAdapter userAdapter = new UserAdapter(users);
        recyclerView.setAdapter(userAdapter);
    }

    private void showDiscounts() {
        DiscountController discountController = DiscountController.getInstance();
        discountController.getAllFireStoreDiscounts(new DiscountController.OnDiscountsLoadedListener() {
            @Override
            public void onDiscountsLoaded(List<Discount> discounts) {
                DiscountAdapter discountAdapter = new DiscountAdapter(
                        discounts,
                        discountController,
                        new DiscountAdapter.OnDiscountEditListener() {
                            @Override
                            public void onDiscountToggled(Discount discount, boolean isActive) {
                                Toast.makeText(
                                        getContext(),
                                        "Descuento " + discount.getCode() + " " + (isActive ? "activado" : "desactivado"),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }

                            @Override
                            public void onDiscountEdit(Discount discount) {
                                showDiscounts();
                            }

                            @Override
                            public void onDiscountDeleted(Discount discount) {
                                showDiscounts();
                                Toast.makeText(
                                        getContext(),
                                        "Descuento " + discount.getCode() + " eliminado",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );

                Button addDiscountButton = view.findViewById(R.id.addDiscountButton);
                addDiscountButton.setOnClickListener(v -> {
                    DiscountCreationDialog dialog = new DiscountCreationDialog(
                            getContext(),
                            discountController,
                            () -> showDiscounts()
                    );
                    dialog.show();
                });

                recyclerView.setAdapter(discountAdapter);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(
                        getContext(),
                        "Error al cargar descuentos: " + e.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}