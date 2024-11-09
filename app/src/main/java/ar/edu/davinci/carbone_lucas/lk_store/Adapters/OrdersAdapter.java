package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ar.edu.davinci.carbone_lucas.lk_store.models.Order.Order;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    private List<Order> orders;
    private Date dateFormat;

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
        this.dateFormat = new Date();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderId;
        private TextView orderDate;
        private TextView orderTotal;
        private TextView orderItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderIdText);
            orderDate = itemView.findViewById(R.id.orderDateText);
            orderTotal = itemView.findViewById(R.id.orderTotalText);
            orderItems = itemView.findViewById(R.id.orderItemsText);
        }

        public void bind(Order order) {
            orderId.setText("Orden #" + order.getId());
            orderDate.setText(order.getDateTime().toString());
            orderTotal.setText(String.format(Locale.getDefault(), "$%.2f", order.getPrice()));

            // Construir el texto de los items
            StringBuilder itemsText = new StringBuilder();
            for (OrderData item : order.getOrderData()) {
                itemsText.append(item.getFoodAmount())
                        .append("x ")
                        .append(item.getName())
                        .append("\n");
            }
            orderItems.setText(itemsText.toString().trim());
        }
    }
}