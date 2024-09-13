package ar.edu.davinci.carbone_lucas.lk_store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private List<Integer> bannerImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Configura la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // ESTUDIAR MEJOR SOBRE ViewPager y IMGs
        // ------------------------------------------
        viewPager = findViewById(R.id.viewPager);

        bannerImages = Arrays.asList(R.drawable.hambur_1, R.drawable.hambur_2, R.drawable.hambur_3);
        BannerAdapter adapter = new BannerAdapter(this, bannerImages);
        viewPager.setAdapter(adapter);
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (viewPager.getCurrentItem() + 1) % bannerImages.size();
                viewPager.setCurrentItem(nextItem);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 8000);
        // ------------------------------------------
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.my_account){
            return true;
        }
        else if(item.getItemId() == R.id.food_menu){
            return true;
        }
        else if(item.getItemId() == R.id.shop_cart){
            return true;
        }
        else if(item.getItemId() == R.id.settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}