package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Drink;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetDrinksApi extends AsyncTask<String, Integer, List<Drink>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/drink";

    @Override
    protected List<Drink> doInBackground(String... params) {
        List<Drink> drinkList = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray drinks = jsonResponse.getJSONArray("drinks");
                for (int i = 0; i < drinks.length(); i++) {
                    JSONObject drinkObject = drinks.getJSONObject(i);
                    Drink newDrink = new Drink(
                            drinkObject.getString("id"),
                            drinkObject.getString("name"),
                            drinkObject.getDouble("price"),
                            drinkObject.getDouble("price"),
                            drinkObject.getString("img_url")
                    );
                    drinkList.add(newDrink);
                    Log.e("GetDrinksApi", "add: " + drinkObject.getString("id"));
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("GetDrinksApi", "Error fetching drinks: " + e.getMessage());
        }

        return drinkList;
    }

    private String makeAPICall(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
