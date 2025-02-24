package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.ImageLoader;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Menu.MenuData;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    private List<MenuData> menuDataList;
    private OnViewProductClickListener viewProductClickListener;
    private OnAddToCartClickListener addToCartClickListener;

    public MenuListAdapter(List<MenuData> menuDataList, OnViewProductClickListener viewProductClickListener, OnAddToCartClickListener addToCartClickListener) {
        this.menuDataList = menuDataList;
        this.viewProductClickListener = viewProductClickListener;
        this.addToCartClickListener = addToCartClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuData menuData = menuDataList.get(position);
        holder.nameTextView.setText("Menu: " + menuData.getId());
        holder.priceTextView.setText(String.format("$%.2f", menuData.getPrice()));
        holder.isDiscountedTextView.setText(menuData.isDiscounted() ? "En Descuento!" : "");
        holder.isDiscountedTextView.setTextColor(menuData.isDiscounted() ? ContextCompat.getColor(holder.itemView.getContext(), R.color.alert) : ContextCompat.getColor(holder.itemView.getContext(), R.color.gotoRegisterLogin));
        ImageLoader.loadImage(holder.imageView, menuData.getImg_url());
        holder.viewProductButton.setOnClickListener(v -> viewProductClickListener.onViewProductClick(menuData));
        holder.addToCartButton.setOnClickListener(v -> addToCartClickListener.onAddToCartClick(menuData));
    }

    @Override
    public int getItemCount() {
        return menuDataList.size();
    }

    public void updateMenuDataList(List<MenuData> newMenuDataList) {
        this.menuDataList = newMenuDataList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        TextView isDiscountedTextView;
        ImageView imageView;
        Button viewProductButton;
        Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_Product_name);
            priceTextView = itemView.findViewById(R.id.textView_menu_price);
            isDiscountedTextView = itemView.findViewById(R.id.textView_is_discounted);
            imageView = itemView.findViewById(R.id.imageView_menu);
            viewProductButton = itemView.findViewById(R.id.button_view_Product);
            addToCartButton = itemView.findViewById(R.id.button_add_to_cart);
        }
    }

    public interface OnViewProductClickListener {
        void onViewProductClick(MenuData menuData);
    }

    public interface OnAddToCartClickListener {
        void onAddToCartClick(MenuData menuData);
    }
}
