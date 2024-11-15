package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostSupportAPI extends AsyncTask<Support, Void, Boolean> {
    private static final String API_URL = "https://lk-store-api.onrender.com/api/support";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected Boolean doInBackground(Support... params) {
        Support support = params[0];

        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", support.getUserId());
            jsonBody.put("email", support.getEmail());
            jsonBody.put("consulta", support.getConsulta());

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException | org.json.JSONException e) {
            Log.e("PostSupportAPI", "Error posting support: " + e.getMessage());
            return false;
        }
    }
}