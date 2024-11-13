package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AsyncTask<String, Integer, Boolean> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/user/register";

    @Override
    protected Boolean doInBackground(String... params) {
        String id = params[0];
        String name = params[1];
        String email = params[2];
        String password = params[3];

        Log.d("User_data", "Intentando registrar usuario:");
        Log.d("User_data", "Nombre: " + name);
        Log.d("User_data", "Email: " + email);
        Log.d("User_data", "Password: " + password);

        return registerUser(id, name, email, password);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.i("User_data", "User registered successfully");
        } else {
            Log.i("User_data", "Error registering user");
        }
    }

    private boolean registerUser(String id, String name, String email, String password) {
        OkHttpClient client = new OkHttpClient();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", id);
            requestBody.put("name", name);
            requestBody.put("email", email);
            requestBody.put("password", password);

            Log.d("User_data", "Request Body: " + requestBody.toString());
        } catch (JSONException e) {
            Log.e("User_data", "Error creando JSON: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json") // Agregar header explícito
                .build();

        try {
            Log.d("User_data", "Enviando request a: " + API_URL);
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Log.d("User_data", "Respuesta del servidor: " + responseBody);
            Log.d("User_data", "Código de respuesta: " + response.code());

            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(responseBody);
                boolean success = jsonObject.getBoolean("success");  // El success está en el objeto raíz

                if (success) {
                    JSONObject result = jsonObject.getJSONObject("result");
                    String userId = result.getString("id");
                    String userEmail = result.getString("email");
                    String userName = result.getString("name");
                    Log.i("User_data", "Registro exitoso - ID: " + userId);
                    return true;
                } else {
                    Log.e("User_data", "Error en el registro - Respuesta: " + responseBody);
                    return false;
                }
            } else {
                Log.e("User_data", "Error en la API: " + response.code() + " - " + response.message());
                Log.e("User_data", "Respuesta de error: " + responseBody);
                return false;
            }
        } catch (IOException | JSONException e) {
            Log.e("User_data", "Error en la comunicación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}