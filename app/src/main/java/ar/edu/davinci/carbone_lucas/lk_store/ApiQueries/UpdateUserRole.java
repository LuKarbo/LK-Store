package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class UpdateUserRole extends AsyncTask<String, Void, Boolean> {
    private boolean isAdminValue;

    public UpdateUserRole(boolean isAdminValue) {
        this.isAdminValue = isAdminValue;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String userId = params[0];
        return updateUserRole(userId);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.i("User_Role", "User role updated successfully");
        } else {
            Log.e("User_Role", "Error updating user role");
        }
    }

    private Boolean updateUserRole(String userId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lk-store-api.onrender.com/api/user/edit/rol/" + userId;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("isAdmin_val", isAdminValue);
        } catch (JSONException e) {
            Log.e("User_Role", "Error creating JSON request body: " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("User_Role", "Response Code: " + response.code());
            Log.d("User_Role", "Response Body: " + response.body().string());

            if (response.isSuccessful()) {
                Log.i("User_Role", "Role update response received");
                return true;
            } else {
                Log.e("User_Role", "Error updating user role: " + response.code() + " - " + response.message());
                return false;
            }
        } catch (IOException e) {
            Log.e("User_Role", "Network Error: " + e.getMessage());
            return false;
        }
    }
}