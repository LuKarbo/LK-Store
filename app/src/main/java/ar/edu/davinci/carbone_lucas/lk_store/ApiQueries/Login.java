package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.util.Log;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Parsers.UserDataParser;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Login extends AsyncTask<String, Integer, User> {

    @Override
    protected User doInBackground(String... params) {
        String userId = params[0];
        return getUserData(userId);
    }

    @Override
    protected void onPostExecute(User user) {
        if (user != null) {
            Log.i("User_Data", user.toString());
        } else {
            Log.e("User_Data", "Error getting user data");
        }
    }

    private User getUserData(String userId) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8787/api/user/" + userId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return UserDataParser.parseUserData(responseBody);
            } else {
                Log.e("User_Data", "Error getting user data: " + response.code() + " - " + response.message());
                return null;
            }
        } catch (IOException e) {
            Log.e("User_Data", "Error getting user data: " + e.getMessage());
            return null;
        }
    }
}