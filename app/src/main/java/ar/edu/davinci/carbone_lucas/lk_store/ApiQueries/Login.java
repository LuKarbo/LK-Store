package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.util.Log;

import ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Parsers.UserDataParser;
import ar.edu.davinci.carbone_lucas.lk_store.Interface.LoginCallback;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Login extends AsyncTask<String, Integer, User> {
    private LoginCallback callback;

    public Login(LoginCallback callback) {
        this.callback = callback;
    }

    @Override
    protected User doInBackground(String... params) {
        String userId = params[0];
        Log.d("User_Data", "Intentando login con userId: " + userId);
        return getUserData(userId);
    }

    @Override
    protected void onPostExecute(User user) {
        if (user != null) {
            Log.i("User_Data", user.toString());
            callback.onLoginSuccess(true);
        } else {
            Log.e("User_Data", "Error getting user data");
            callback.onLoginError("Error getting user data");
        }
    }

    private User getUserData(String userId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lk-store-api.onrender.com/api/user/" + userId;
        Log.d("User_Data", "URL llamada: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Log.d("User_Data", "Enviando request...");
            Response response = client.newCall(request).execute();
            Log.d("User_Data", "Código de respuesta: " + response.code());

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                Log.d("User_Data", "Respuesta: " + responseBody);
                return UserDataParser.parseUserData(responseBody);
            } else {
                String responseBody = response.body().string();
                Log.e("User_Data", "Error getting user data: " + response.code() + " - " + response.message());
                Log.e("User_Data", "Response body: " + responseBody);
                return null;
            }
        } catch (IOException e) {
            Log.e("User_Data", "Error getting user data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}