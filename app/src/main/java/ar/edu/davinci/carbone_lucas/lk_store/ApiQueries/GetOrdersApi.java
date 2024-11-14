package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetOrdersApi extends AsyncTask<String, Integer, List<Order>> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/orders";

    @Override
    protected List<Order> doInBackground(String... params) {
        List<Order> orders = new ArrayList<>();

        try {
            String response = makeAPICall(API_URL);
            JSONObject jsonResponse = new JSONObject(response);

            if (jsonResponse.getBoolean("success")) {
                JSONArray ordersArray = jsonResponse.getJSONArray("orders");
                for (int i = 0; i < ordersArray.length(); i++) {
                    JSONObject orderJson = ordersArray.getJSONObject(i);
                    String id = orderJson.getString("id");
                    String userId = orderJson.getString("user");
                    JSONArray orderDataArray = orderJson.getJSONArray("orderData");
                    List<OrderData> orderData = new ArrayList<>();
                    for (int j = 0; j < orderDataArray.length(); j++) {
                        JSONObject orderDataItem = orderDataArray.getJSONObject(j);
                        int foodAmount = orderDataItem.getInt("foodAmount");
                        String foodID = orderDataItem.getString("foodID");
                        String foodType = orderDataItem.getString("foodType");
                        orderData.add(new OrderData(foodAmount, foodID, getFoodType(foodType)));
                    }
                    String dateTimeString = orderJson.getString("dateTime");
                    Date dateTime;

                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dateTime = formatter.parse(dateTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        dateTime = new Date();
                    }

                    Order order = new Order(id, userId, orderData, dateTime);
                    orders.add(order);
                }
            }
        } catch (IOException | JSONException e) {
            Log.e("GetOrdersApi", "Error fetching orders: " + e.getMessage());
        }

        return orders;
    }

    private String makeAPICall(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String getFoodType(String foodType) {
        switch (foodType){
            case "hamburger":
                return "hamburguesa";
            case "fries":
                return "papas fritas";
            case "drink":
                return "bebida";
            case "menu":
                return "menu";
        }
        return "";
    }
}