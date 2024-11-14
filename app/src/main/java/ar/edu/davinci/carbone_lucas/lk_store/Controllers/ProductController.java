package ar.edu.davinci.carbone_lucas.lk_store.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetDrinksApi;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetFriesApi;
import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.GetHamburguersAPI;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductController {
    private static ProductController instance;

    private List<Hamburger> hamburgerList;
    private List<Fries> friesList;
    private List<Drink> drinkList;

    private ProductController() {
        loadData();
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    private void loadData() {
        GetHamburguersAPI hamburgerTask = new GetHamburguersAPI();
        GetFriesApi friesTask = new GetFriesApi();
        GetDrinksApi drinkTask = new GetDrinksApi();

        try {
            hamburgerList = hamburgerTask.execute().get();
            friesList = friesTask.execute().get();
            drinkList = drinkTask.execute().get();
        } catch (Exception e) {
            Log.e("ProductController", "Error loading food data: " + e.getMessage());
        }
    }

    public List<Hamburger> getHamburgers() {
        return hamburgerList;
    }

    public List<Fries> getFries() {
        return friesList;
    }

    public List<Drink> getDrinks() {
        return drinkList;
    }

    public Hamburger getHamburger(String id) {
        for (Hamburger item : hamburgerList) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return new Hamburger("100000", "", false, "TEST", 5.99, 50, "https://example.com/cheeseburger.jpg");
    }

    public Fries getFries(String id) {
        for (Fries item : friesList) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return new Fries("100000", "", false, "TEST", 2.49, 100, "https://example.com/small-fries.jpg");
    }

    public Drink getDrink(String id) {
        for (Drink item : drinkList) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return new Drink("10000", "TESt", 2.29, 150, "https://example.com/cola.jpg");
    }
}