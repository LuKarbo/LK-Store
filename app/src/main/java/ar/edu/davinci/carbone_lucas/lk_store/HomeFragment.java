package ar.edu.davinci.carbone_lucas.lk_store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        handler.postDelayed(runnable, 8000); // Empieza después de 8 segundos

        // CARGO LISTA DE BOTONES
        descuentos = view.findViewById(R.id.title_descuentos);
        descuentos.setText("Descuentos Destacados!");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<CardItem> items = new ArrayList<>();
        items.add(new CardItem("Title 1", "$10.00", R.drawable.hambur_1));
        items.add(new CardItem("Title 2", "$12.00", R.drawable.hambur_2));
        items.add(new CardItem("Title 3", "$30.00", R.drawable.hambur_1));
        items.add(new CardItem("Title 2", "$122.00", R.drawable.hambur_1));
        items.add(new CardItem("Title 1", "$10.00", R.drawable.hambur_1));
        items.add(new CardItem("Title 2", "$12.00", R.drawable.hambur_2));
        items.add(new CardItem("Title 3", "$30.00", R.drawable.hambur_1));
        items.add(new CardItem("Title 2", "$122.00", R.drawable.hambur_1));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new CardAdapter(items));
    }
}