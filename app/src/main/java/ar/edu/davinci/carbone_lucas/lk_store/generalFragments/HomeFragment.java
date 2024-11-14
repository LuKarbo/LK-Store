package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

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

import ar.edu.davinci.carbone_lucas.lk_store.Adapters.BannerAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Adapters.CardAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Adapters.RecommendedMenuAdapter;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.MenuController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.OrderController;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.ProductController;
import ar.edu.davinci.carbone_lucas.lk_store.MainActivity;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.CardItem;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;
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
        ProductController productService = ProductController.getInstance();
        MenuController menuController = MenuController.getInstance();
        List<Hamburger> hamburguesas = productService.getHamburgers();
        List<Fries> papasFritas = productService.getFries();
        List<Drink> bebidas = productService.getDrinks();

        // Seleccionar los productos con descuento
        List<CardItem> items = new ArrayList<>();
        items.addAll(getDiscountedProducts(hamburguesas, 3, "Hamburguesa"));
        items.addAll(getDiscountedProducts(papasFritas, 3, "Papas Fritas"));

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
                //Log.i("Ver Menu: ", menuData.getId());
                ViewProductFragment viewProductFragment = ViewProductFragment.newInstance(menuData.getId(), menuData.getType());
                ((MainActivity) requireActivity()).replaceFragment(viewProductFragment);
            }

            @Override
            public void onAddToCartClick(MenuData menuData) {
                if (menuData != null) {
                    Toast.makeText(getContext(), "Agregado al carrito", Toast.LENGTH_SHORT).show();
                    OrderController oc = OrderController.getInstance(User.getInstance().getUserId());
                    oc.addItem(menuData.getType(), menuData.getId(), 1);
                } else {
                    Toast.makeText(getContext(), "No se pudo agregar al carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerViewRecommendedMenus.setAdapter(adapterMenu);


        // Agrego funcionalidad al boton de las categorías
        cardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CardItem clickedItem = items.get(position);
                ViewProductFragment viewProductFragment = ViewProductFragment.newInstance(clickedItem.getId(), clickedItem.getType());
                ((MainActivity) requireActivity()).replaceFragment(viewProductFragment);
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
                ProductListFragment productListFragment = ProductListFragment.newInstance("Hamburguesas");
                ((MainActivity) requireActivity()).replaceFragment(productListFragment);
            }
        });

        papas_btn = view.findViewById(R.id.papas_list);
        papas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListFragment productListFragment = ProductListFragment.newInstance("Papas Fritas");
                ((MainActivity) requireActivity()).replaceFragment(productListFragment);
            }
        });

        bebidas_btn = view.findViewById(R.id.drink_list);
        bebidas_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListFragment productListFragment = ProductListFragment.newInstance("Bebidas");
                ((MainActivity) requireActivity()).replaceFragment(productListFragment);
            }
        });

        soporte_btn = view.findViewById(R.id.soporte);
        soporte_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportFragment supportFragment = SupportFragment.newInstance();
                ((MainActivity) requireActivity()).replaceFragment(supportFragment);
            }
        });

    }

    private <T extends Product> List<CardItem> getDiscountedProducts(List<T> products, int maxItems, String type) {
        List<CardItem> discountedProducts = new ArrayList<>();
        int count = 0;
        for (T product : products) {
            if (product.isDiscounted() && count < maxItems) {
                discountedProducts.add(new CardItem(
                        product.getId(),
                        product.getName(),
                        String.format("$%.2f", product.getPrice()),
                        // cambiar por el proceso de imagen api: product.getImg_url()
                        R.drawable.hambur_1,
                        type
                ));
                count++;
            }
        }
        return discountedProducts;
    }
}