package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.AdminFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.HomeFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.MenuListFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.MyAccountFragment;
import ar.edu.davinci.carbone_lucas.lk_store.generalFragments.ShopCartFragment;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

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

        // Obtener el ítem del menú admin
        MenuItem adminMenuItem = menu.findItem(R.id.adminmenu);

        // Verificar si existe el ítem y controlar su visibilidad según isAdmin
        if (adminMenuItem != null) {
            User currentUser = User.getInstance();
            adminMenuItem.setVisible(currentUser.isAdmin());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("User_Data", "Login 3: " + User.getInstance().isAdmin());

        if (item.getItemId() == R.id.home_menu_btn) {
            Fragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, homeFragment);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.my_account) {
            Fragment myAccountFragment = new MyAccountFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, myAccountFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.food_menu) {
            MenuListFragment menuListFragment = new MenuListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, menuListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.shop_cart) {
            Fragment shopCartFragment = new ShopCartFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, shopCartFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        } else if (item.getItemId() == R.id.adminmenu && User.getInstance().isAdmin()) {
            Fragment adminFragment = new AdminFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_data, adminFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_data, newFragment)
                .addToBackStack(null)
                .commit();
    }
}