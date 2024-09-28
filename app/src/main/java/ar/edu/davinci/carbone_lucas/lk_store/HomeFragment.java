package ar.edu.davinci.carbone_lucas.lk_store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.CardItem;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private List<Integer> bannerImages;
    private TextView descuentos;

    // botones de categoria
    private ImageButton hambur_btn;
    private ImageButton papas_btn;
    private ImageButton bebidas_btn;
    private ImageButton soporte_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // CARGO BANNERS
        viewPager = view.findViewById(R.id.viewPager);
        bannerImages = Arrays.asList(R.drawable.hambur_1, R.drawable.hambur_2, R.drawable.hambur_3);
        BannerAdapter adapter = new BannerAdapter(requireContext(), bannerImages);
        viewPager.setAdapter(adapter);
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (viewPager.getCurrentItem() + 1) % bannerImages.size();
                viewPager.setCurrentItem(nextItem);
                handler.postDelayed(this, 3000); // Cambia cada 3 segundos
            }
        };
        handler.postDelayed(runnable, 4000); // Empieza después de 4 segundos

        // CARGO LISTA DE BOTONES
        descuentos = view.findViewById(R.id.title_descuentos);
        descuentos.setText("Descuentos Destacados!");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<CardItem> items = new ArrayList<>();
        items.add(new CardItem(1,"Doble Queso XL", "$350.00", R.drawable.hambur_1));
        items.add(new CardItem(2,"Master PS", "$180.00", R.drawable.hambur_2));
        items.add(new CardItem(3,"Simple X", "$300.00", R.drawable.hambur_3));
        items.add(new CardItem(4,"Papas Fresh", "$122.00", R.drawable.hambur_1));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CardAdapter cardAdapter = new CardAdapter(items);
        recyclerView.setAdapter(cardAdapter);

        // Agrego funcionalidad al boton de las categorías
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CardItem clickedItem = items.get(position);
                Toast.makeText(getContext(), "Descuento: " + clickedItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        // Cargo funcionalidad de Botones de Categorías:
        crearFuncionalidadBTNS(view);
    }

    private void crearFuncionalidadBTNS(View view) {
        hambur_btn = view.findViewById(R.id.burger_list);
        hambur_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Listado de Hamburguesas!", Toast.LENGTH_SHORT).show();
            }
        });

        papas_btn = view.findViewById(R.id.papas_list);
        papas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Listado de Papas Fritas!", Toast.LENGTH_SHORT).show();
            }
        });

        bebidas_btn = view.findViewById(R.id.drink_list);
        bebidas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Listado de Bebidas!", Toast.LENGTH_SHORT).show();
            }
        });

        soporte_btn = view.findViewById(R.id.soporte);
        soporte_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Soporte/Ayuda!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}