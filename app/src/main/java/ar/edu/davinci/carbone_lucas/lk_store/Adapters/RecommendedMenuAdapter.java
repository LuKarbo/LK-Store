package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ImageLoader;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class RecommendedMenuAdapter extends RecyclerView.Adapter<RecommendedMenuAdapter.ViewHolder> {

    private List<MenuData> menusList;
    private OnItemClickListener onItemClickListener;

    public RecommendedMenuAdapter(List<MenuData> menusList) {
        this.menusList = menusList;
    }

    public interface OnItemClickListener {
        void onViewMenuClick(MenuData menuData);
        void onAddToCartClick(MenuData menuData);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommended_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuData menuData = menusList.get(position);

        // Establecer los valores en la vista
        ImageLoader.loadImage(holder.imageViewMenu, menuData.getImg_url());
        holder.textViewMenuName.setText(menuData.getHamburger().getName() + " | " + menuData.getFries().getName() + " | " + menuData.getDrink().getName());
        holder.textViewMenuPrice.setText(String.format("$%.2f", menuData.getPrice()));

        holder.buttonViewMenu.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onViewMenuClick(menuData);
            }
        });

        holder.buttonAddToCart.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onAddToCartClick(menuData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMenu;
        TextView textViewMenuName;
        TextView textViewMenuPrice;
        Button buttonViewMenu;
        Button buttonAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMenu = itemView.findViewById(R.id.imageView_menu);
            textViewMenuName = itemView.findViewById(R.id.textView_menu_name);
            textViewMenuPrice = itemView.findViewById(R.id.textView_menu_price);
            buttonViewMenu = itemView.findViewById(R.id.button_view_menu);
            buttonAddToCart = itemView.findViewById(R.id.button_add_to_cart);
        }
    }
}