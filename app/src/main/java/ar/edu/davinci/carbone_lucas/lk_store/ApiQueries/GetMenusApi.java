package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.Menu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMenusApi extends AsyncTask<String, Integer, List<Menu>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/menu";

    @Override
    protected List<Menu> doInBackground(String... params) {
        List<Menu> allMenus = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray menuArray = jsonResponse.getJSONArray("menus");
                for (int i = 0; i < menuArray.length(); i++) {
                    JSONObject menuJson = menuArray.getJSONObject(i);
                    String id = menuJson.getString("id");
                    String hamburgerId = menuJson.getJSONObject("hamburger").getString("id");
                    String friesId = menuJson.getJSONObject("fries").getString("id");
                    String drinkId = menuJson.getJSONObject("drink").getString("id");
                    String discount = menuJson.getString("discount");
                    boolean isDiscounted = menuJson.getBoolean("isDiscounted");
                    double totalPrice = menuJson.getDouble("totalPrice");

                    allMenus.add(new Menu(id, hamburgerId, friesId, drinkId, discount, isDiscounted, totalPrice));
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("GetMenusApi", "Error fetching menus: " + e.getMessage());
        }

        return allMenus;
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