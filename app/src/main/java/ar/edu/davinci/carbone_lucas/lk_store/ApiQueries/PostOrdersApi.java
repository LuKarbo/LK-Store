package ar.edu.davinci.carbone_lucas.lk_store.ApiQueries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostOrdersApi extends AsyncTask<Order, Void, Boolean> {

    private static final String API_URL = "https://lk-store-api.onrender.com/api/order";
    private OnPostOrderListener listener;

    public interface OnPostOrderListener {
        void onPostOrderSuccess();
        void onPostOrderFailure(String errorMessage);
    }

    public void setOnPostOrderListener(OnPostOrderListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Order... params) {
        Order order = params[0];
        try {
            String response = makeAPICall(API_URL, order);
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getBoolean("success");
        } catch (IOException | JSONException e) {
            Log.e("PostOrdersApi", "Error creating order: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            if (listener != null) {
                listener.onPostOrderSuccess();
            }
        } else {
            if (listener != null) {
                listener.onPostOrderFailure("Error creating order");
            }
        }
    }

    private String makeAPICall(String url, Order order) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        JSONObject orderData = new JSONObject();
        orderData.put("user", order.getUserId());
        JSONArray orderDataArray = new JSONArray();
        for (OrderData item : order.getOrderData()) {
            JSONObject orderDataItem = new JSONObject();
            orderDataItem.put("foodAmount", item.getFoodAmount());
            orderDataItem.put("foodID", item.getFoodId());
            switch (item.getFoodType()){
                case "Hamburguesa":
                    orderDataItem.put("foodType", "hamburger");
                    break;
                case "Papas Fritas":
                    orderDataItem.put("foodType", "fries");
                    break;
                case "Bebida":
                    orderDataItem.put("foodType", "drink");
                    break;
                case "Menu":
                    orderDataItem.put("foodType", "menu");
                    break;
            }
            orderDataArray.put(orderDataItem);
        }
        orderData.put("orderData", orderDataArray);
        orderData.put("price", order.getPrice());
        orderData.put("dateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getDateTime()));

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), orderData.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}