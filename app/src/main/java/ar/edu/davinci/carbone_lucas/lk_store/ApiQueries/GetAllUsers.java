package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.UserDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAllUsers extends AsyncTask<Void, Void, List<UserDTO>> {

    private OnGetAllUsersListener listener;

    public interface OnGetAllUsersListener {
        void onGetAllUsersSuccess(List<UserDTO> userDTOs);
        void onGetAllUsersFailure(String errorMessage);
    }

    public void setOnGetAllUsersListener(OnGetAllUsersListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<UserDTO> doInBackground(Void... params) {
        return getAllUserData();
    }

    @Override
    protected void onPostExecute(List<UserDTO> userDTOs) {
        if (userDTOs != null) {
            if (listener != null) {
                listener.onGetAllUsersSuccess(userDTOs);
            }
        } else {
            if (listener != null) {
                listener.onGetAllUsersFailure("Error getting user data");
            }
        }
    }

    private List<UserDTO> getAllUserData() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lk-store-api.onrender.com/api/user";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseUserDataResponse(responseBody);
            } else {
                Log.e("User_Data", "Error getting user data: " + response.code() + " - " + response.message());
                return null;
            }
        } catch (IOException e) {
            Log.e("User_Data", "Error getting user data: " + e.getMessage());
            return null;
        }
    }

    private List<UserDTO> parseUserDataResponse(String responseBody) {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            if (jsonResponse.getBoolean("success")) {
                List<UserDTO> userDTOs = new ArrayList<>();
                JSONArray usersJsonArray = jsonResponse.getJSONArray("users");
                for (int i = 0; i < usersJsonArray.length(); i++) {
                    JSONObject userJson = usersJsonArray.getJSONObject(i);
                    UserDTO userDTO = new UserDTO(
                            userJson.getString("id"),
                            userJson.getString("name"),
                            userJson.getString("email"),
                            userJson.getString("phoneNumber"),
                            userJson.getString("address"),
                            userJson.getBoolean("isAdmin")
                    );
                    userDTOs.add(userDTO);
                }
                return userDTOs;
            } else {
                Log.e("User_Data", "Error getting user data: " + jsonResponse.getString("message"));
                return null;
            }
        } catch (JSONException e) {
            Log.e("User_Data", "Error parsing user data: " + e.getMessage());
            return null;
        }
    }
}