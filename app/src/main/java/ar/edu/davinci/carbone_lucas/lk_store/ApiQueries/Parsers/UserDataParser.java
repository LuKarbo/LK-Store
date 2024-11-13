package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries.Parsers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class UserDataParser {
    public static User parseUserData(String userDataJson) {
        try {
            JSONObject userJson = new JSONObject(userDataJson);

            if (userJson.getBoolean("success")) {
                JSONObject userData = userJson.getJSONObject("user");

                User user = User.getInstance();
                user.setId(userData.getString("id"));
                user.setEmail(userData.getString("email"));
                user.setPhoneNumber(userData.getString("phoneNumber"));
                user.setAdmin(userData.getBoolean("isAdmin"));
                user.setName(userData.getString("name"));
                user.setAddress(userData.getString("address"));
                Log.d("User_Data", "user: " + user.getUserId() + " | " +  user.isAdmin());
                return user;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}