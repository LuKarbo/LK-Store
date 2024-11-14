package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Hamburger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetHamburguersAPI extends AsyncTask<String, Integer, List<Hamburger>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/hamburger";

    @Override
    protected List<Hamburger> doInBackground(String... params) {
        List<Hamburger> hamburgerList = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray hamburgers = jsonResponse.getJSONArray("hamburgers");
                for (int i = 0; i < hamburgers.length(); i++) {
                    JSONObject hamburger = hamburgers.getJSONObject(i);
                    Hamburger newHamburger = new Hamburger(
                            hamburger.getString("id"),
                            hamburger.optString("discountId", null),
                            hamburger.getBoolean("isDiscounted"),
                            hamburger.getString("name"),
                            hamburger.getDouble("originalPrice"),
                            hamburger.getDouble("finalPrice"),
                            hamburger.getString("img_url")
                    );
                    hamburgerList.add(newHamburger);
                    Log.e("GetHamburguersAPI", "add: " + hamburger.getString("id"));
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("GetHamburguersAPI", "Error fetching hamburgers: " + e.getMessage());
        }

        return hamburgerList;
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