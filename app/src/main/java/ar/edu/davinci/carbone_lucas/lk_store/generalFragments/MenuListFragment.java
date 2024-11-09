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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.MenuListAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.MainActivity;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class MenuListFragment extends Fragment {
    private String category = "Menu";
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private MenuListAdapter adapter;
    private List<MenuData> menuDataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        TextView titleTextView = view.findViewById(R.id.product_title);
        titleTextView.setText(String.format(Locale.getDefault(), "Categoría: %s", category));

        searchEditText = view.findViewById(R.id.product_search);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuController menuController = new MenuController();
        menuDataList = menuController.getAllMenus();
        adapter = new MenuListAdapter(menuDataList, (menuData) -> {
            ViewProductFragment viewProductFragment = ViewProductFragment.newInstance(menuData.getId(), menuData.getType());
            ((MainActivity) requireActivity()).replaceFragment(viewProductFragment);
        }, (menuData) -> {
            Toast.makeText(getContext(), "Agregar al carrito: " + menuData.getId() + " | " + menuData.getType(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMenuData(s.toString());
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

    private void filterMenuData(String query) {
        if (menuDataList != null) {
            List<MenuData> filteredList = menuDataList.stream()
                    .filter(menuData -> String.valueOf(menuData.getId()).toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            adapter.updateMenuDataList(filteredList);
        }
    }
}