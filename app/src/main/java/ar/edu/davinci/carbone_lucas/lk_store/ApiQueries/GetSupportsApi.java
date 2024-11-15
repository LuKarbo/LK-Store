package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSupportsApi extends AsyncTask<String, Integer, List<Support>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/support";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    @Override
    protected List<Support> doInBackground(String... params) {
        List<Support> allSupports = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray supportsArray = jsonResponse.getJSONArray("supports");
                for (int i = 0; i < supportsArray.length(); i++) {
                    JSONObject supportJson = supportsArray.getJSONObject(i);
                    String id = supportJson.getString("id");
                    String userId = supportJson.getString("userId");
                    String email = supportJson.getString("email");
                    String consulta = supportJson.getString("consulta");
                    Date fecha = DATE_FORMAT.parse(supportJson.getString("fecha"));
                    String respuesta = supportJson.optString("respuesta", "");
                    Date fechaRespuesta = supportJson.has("fechaRespuesta")
                            ? DATE_FORMAT.parse(supportJson.getString("fechaRespuesta"))
                            : null;

                    Support support = new Support(id, userId, email, consulta);
                    support.setFecha(fecha);
                    support.setRespuesta(respuesta);
                    support.setFechaRespuesta(fechaRespuesta);
                    allSupports.add(support);
                }
            }
        } catch (IOException | JSONException | ParseException e) {
            Log.e("GetSupportsApi", "Error fetching supports: " + e.getMessage());
        }

        return allSupports;
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