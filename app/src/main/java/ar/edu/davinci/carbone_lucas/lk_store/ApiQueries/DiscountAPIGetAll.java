package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscountAPIGetAll extends AsyncTask<String, Integer, List<Discount>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/discount";

    @Override
    protected List<Discount> doInBackground(String... params) {
        List<Discount> discounts = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray discount_respuesta = jsonResponse.getJSONArray("discounts");
                for (int i = 0; i < discount_respuesta.length(); i++) {
                    JSONObject discount = discount_respuesta.getJSONObject(i);
                    discounts.add(new Discount(discount.getString("id"),discount.getString("code"),discount.getDouble("porcent"),discount.getBoolean("isActive")));
                    Log.e("DiscountAPIGetAll", "Added discount ID: " + discount.getString("id"));
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("DiscountAPIGetAll", "Error fetching discounts: " + e.getMessage());
        }

        return discounts;
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
