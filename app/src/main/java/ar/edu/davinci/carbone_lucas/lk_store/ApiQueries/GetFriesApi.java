package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Food.Fries;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetFriesApi extends AsyncTask<String, Integer, List<Fries>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/fries";

    @Override
    protected List<Fries> doInBackground(String... params) {
        List<Fries> friesList = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray fries = jsonResponse.getJSONArray("fries");
                for (int i = 0; i < fries.length(); i++) {
                    JSONObject friesObject = fries.getJSONObject(i);
                    Fries newFries = new Fries(
                            friesObject.getString("id"),
                            friesObject.optString("discountId", null),
                            friesObject.getBoolean("isDiscounted"),
                            friesObject.getString("name"),
                            friesObject.getDouble("finalPrice"),
                            friesObject.getDouble("originalPrice"),
                            friesObject.getString("img_url")
                    );
                    friesList.add(newFries);
                    Log.e("GetFriesApi", "add: " + friesObject.getString("id"));
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("GetFriesApi", "Error fetching fries: " + e.getMessage());
        }

        return friesList;
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