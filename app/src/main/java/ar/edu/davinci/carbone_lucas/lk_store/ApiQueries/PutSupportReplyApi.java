package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PutSupportReplyApi extends AsyncTask<String, Void, Boolean> {
    private static final String API_URL = "https://lk-store-api.onrender.com/api/support/%s/reply";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    protected Boolean doInBackground(String... params) {
        String id = params[0];
        String respuesta = params[1];

        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("respuesta", respuesta);

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            Request request = new Request.Builder()
                    .url(String.format(API_URL, id))
                    .put(body)
                    .build();

            Log.d("PutSupportReplyApi", "Sending request to: " + request.url().toString());
            Response response = client.newCall(request).execute();
            Log.d("PutSupportReplyApi", "Response code: " + response.code());
            return response.isSuccessful();
        } catch (IOException | org.json.JSONException e) {
            Log.e("PutSupportReplyApi", "Error putting support reply: " + e.getMessage());
            return false;
        }
    }
}