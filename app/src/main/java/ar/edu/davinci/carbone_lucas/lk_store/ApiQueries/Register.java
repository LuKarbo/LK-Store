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

    private static final String API_URL = "http://localhost:8787/api/user/register";

    @Override
    protected Boolean doInBackground(String... params) {
        String name = params[0];
        String email = params[1];
        String password = params[2];
        return registerUser(name, email, password);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.i("User_data", "User registered successfully");
        } else {
            Log.i("User_data", "Error registering user");
        }
    }

    private boolean registerUser(String name, String email, String password) {
        OkHttpClient client = new OkHttpClient();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Log.i("User_data", responseBody);

                JSONObject jsonObject = new JSONObject(responseBody);
                boolean success = jsonObject.getJSONObject("result").getBoolean("success");
                if (success) {
                    JSONObject userObject = jsonObject.getJSONObject("result");
                    String userId = userObject.getString("id");
                    String userEmail = userObject.getString("email");
                    String userName = userObject.getString("name");
                    Log.i("User_data", "User ID: " + userId);
                    Log.i("User_data", "User Email: " + userEmail);
                    Log.i("User_data", "User Name: " + userName);
                    return true;
                } else {
                    Log.i("User_data", "Error registering user");
                    return false;
                }
            } else {
                Log.i("User_data", "Error connecting to API: " + response.code() + " - " + response.message());
                return false;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}