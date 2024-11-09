package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.OrderController;
import ar.edu.davinci.carbone_lucas.lk_store.models.Order.OrderData;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<OrderData> items = new ArrayList<>();
    private OrderController orderController;
    private CartAdapterListener listener;

    public interface CartAdapterListener {
        void onItemChanged();
    }

    public CartAdapter(OrderController orderController, CartAdapterListener listener) {
        this.orderController = orderController;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        OrderData item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<OrderData> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemQuantity;
        private Button increaseButton;
        private Button decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
        }

        public void bind(OrderData item) {



            itemName.setText(String.format("%s", item.getName()));
            itemQuantity.setText(String.valueOf(item.getFoodAmount()));

            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderController.updateItemAmount(item.getFoodId(), item.getFoodAmount() + 1);
                    notifyItemChanged(getAdapterPosition());
                    if (listener != null) {
                        listener.onItemChanged();
                    }
                    decreaseButton.setText("-");
                }
            });

            if(item.getFoodAmount() == 1){
                decreaseButton.setText("Eliminar");
            }

            decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.getFoodAmount() > 1) {
                        orderController.updateItemAmount(item.getFoodId(), item.getFoodAmount() - 1);
                        notifyItemChanged(getAdapterPosition());
                    } else {
                        orderController.removeItem(item.getFoodId());
                    }
                    if (listener != null) {
                        listener.onItemChanged();
                    }
                }
            });
        }
    }
}
