package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Parsers.UserDataParser;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class UpdateUser extends AsyncTask<String, Void, Boolean> {
    private String name;
    private String phoneNumber;
    private String address;

    public UpdateUser(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String userId = params[0];
        return updateUserData(userId);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.i("User_Data", "User data updated successfully");
        } else {
            Log.e("User_Data", "Error updating user data");
        }
    }

    private Boolean updateUserData(String userId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lk-store-api.onrender.com/api/user/edit/" + userId;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("address", address);
        } catch (JSONException e) {
            Log.e("User_Data", "Error creating JSON request body: " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                User user = UserDataParser.parseUserData(responseBody);
                if (user != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Log.e("User_Data", "Error updating user data: " + response.code() + " - " + response.message());
                return false;
            }
        } catch (IOException e) {
            Log.e("User_Data", "Error updating user data: " + e.getMessage());
            return false;
        }
    }
}