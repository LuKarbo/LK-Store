package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.MenuFoodFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.MyAccountFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.ShopCartFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    // Pase 1 agregar la creación del auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pase 2 Inizalizo mi Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Paso 3 Verifico si esta logeado, sino lo mando para el login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Configura la Toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Cargar el Fragmento Home por defecto
            if (savedInstanceState == null) {
                Fragment homeFragment = new HomeFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_data, homeFragment);
                fragmentTransaction.commit();
            }
        }
        else{
            // paso 4 lo mando para el login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home_menu_btn) {
            Fragment homeFragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, homeFragment);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.my_account) {
            Fragment myAccountFragment = new MyAccountFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, myAccountFragment);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.food_menu) {
            Fragment menuFoodFragment = new MenuFoodFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, menuFoodFragment);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.shop_cart) {
            Fragment shopCartFragment = new ShopCartFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, shopCartFragment);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.settings) {
            Fragment settingsFragment = new SettingsFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, settingsFragment);
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}