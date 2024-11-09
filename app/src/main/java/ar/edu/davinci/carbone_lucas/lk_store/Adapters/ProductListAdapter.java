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

import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.interfaces.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<? extends Product> productList;
    private OnViewProductClickListener viewProductClickListener;
    private OnAddToCartClickListener addToCartClickListener;

    public ProductListAdapter(List<?> productList, OnViewProductClickListener viewProductClickListener, OnAddToCartClickListener addToCartClickListener) {
        this.productList = (List<? extends Product>) productList;
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
        Product product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.isDiscountedTextView.setText(product.isDiscounted() ? "En Descuento!" : "");
        holder.isDiscountedTextView.setTextColor(product.isDiscounted() ? ContextCompat.getColor(holder.itemView.getContext(), R.color.alert) : ContextCompat.getColor(holder.itemView.getContext(), R.color.gotoRegisterLogin));
        holder.imageView.setImageURI(Uri.parse(product.getImg_url()));
        holder.viewProductButton.setOnClickListener(v -> viewProductClickListener.onViewProductClick(product));
        holder.addToCartButton.setOnClickListener(v -> addToCartClickListener.onAddToCartClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProductList(List<? extends Product> newProductList) {
        this.productList = newProductList;
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
        void onViewProductClick(Product product);
    }

    public interface OnAddToCartClickListener {
        void onAddToCartClick(Product product);
    }
}