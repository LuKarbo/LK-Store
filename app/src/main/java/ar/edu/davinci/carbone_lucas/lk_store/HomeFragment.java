package ar.edu.davinci.carbone_lucas.lk_store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;
import ar.edu.davinci.carbone_lucas.lk_store.models.CardItem;
import ar.edu.davinci.carbone_lucas.lk_store.models.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;
import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

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
    private RecyclerView recyclerViewRecommendedMenus;

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

        // Obtener los productos de la API
        ProductController productService = new ProductController();
        MenuController menuController = new MenuController();
        List<Hamburger> hamburguesas = productService.getHamburgers();
        List<Fries> papasFritas = productService.getFries();
        List<Drink> bebidas = productService.getDrinks();

        // Seleccionar los productos con descuento
        List<CardItem> items = new ArrayList<>();
        items.addAll(getDiscountedProducts(hamburguesas, 3));
        items.addAll(getDiscountedProducts(papasFritas, 3));

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CardAdapter cardAdapter = new CardAdapter(items);
        recyclerView.setAdapter(cardAdapter);

        // Recupero los Menus en Descuento
        List<MenuData> menus_list = menuController.getDiscountedMenus();

        // Configuro el RecyclerView para los menús recomendados
        recyclerViewRecommendedMenus = view.findViewById(R.id.recyclerView_recommended_menus);
        recyclerViewRecommendedMenus.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecommendedMenuAdapter adapterMenu = new RecommendedMenuAdapter(menus_list);
        adapterMenu.setOnItemClickListener(new RecommendedMenuAdapter.OnItemClickListener() {
            @Override
            public void onViewMenuClick(MenuData menuData) {
                // Lógica para ver el menú
                Toast.makeText(getContext(), "Ver menú: " + menuData.getHamburger().getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddToCartClick(MenuData menuData) {
                // Lógica para agregar al carrito
                Toast.makeText(getContext(), "Agregar al carrito: " + menuData.getHamburger().getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewRecommendedMenus.setAdapter(adapterMenu);


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

    private <T extends Product> List<CardItem> getDiscountedProducts(List<T> products, int maxItems) {
        List<CardItem> discountedProducts = new ArrayList<>();
        int count = 0;
        for (T product : products) {
            if (product.isDiscounted() && count < maxItems) {
                discountedProducts.add(new CardItem(
                        product.getDiscountId(),
                        product.getName(),
                        String.format("$%.2f", product.getPrice()),
                        // cambiar por el proceso de imagen api: product.getImg_url()
                        R.drawable.hambur_1
                ));
                count++;
            }
        }
        return discountedProducts;
    }
}