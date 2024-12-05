package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;
import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {
    private List<Discount> discounts;
    private DiscountController discountController;
    private OnDiscountEditListener editListener;
    private Context context;

    public interface OnDiscountEditListener {
        void onDiscountToggled(Discount discount, boolean isActive);
        void onDiscountDeleted(Discount discount);
        void onDiscountEdit(Discount discount);
    }

    public DiscountAdapter(List<Discount> discounts, DiscountController discountController, OnDiscountEditListener listener) {
        this.discounts = discounts;
        this.discountController = discountController;
        this.editListener = listener;
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_discount, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        Discount discount = discounts.get(position);
        holder.discountCode.setText(discount.getCode());
        holder.discountPercentTV.setText(discount.getPercent() + "%");
        holder.discountActiveSwitch.setChecked(discount.isActive());
        if(discount.isActive()){
            holder.discountActiveSwitch.setText("Activo");
        }
        else{
            holder.discountActiveSwitch.setText("Desactivado");
        }

        holder.discountActiveSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            discountController.updateDiscountStatus(discount.getId(), isChecked, new DiscountController.OnDiscountOperationListener() {
                @Override
                public void onSuccess(Discount discount) {
                    holder.discountActiveSwitch.setChecked(isChecked);
                    if(isChecked){
                        holder.discountActiveSwitch.setText("Activo");
                    }
                    else{
                        holder.discountActiveSwitch.setText("Desactivado");
                    }
                    Toast.makeText(context, "Descuento actualizado correctamente", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(context, "Error al actualizar el descuento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.editButton.setOnClickListener(v -> {
            DiscountCreationDialog dialog = new DiscountCreationDialog(
                    context,
                    discountController,
                    discount,
                    () -> {
                        editListener.onDiscountEdit(discount);
                    }
            );
            dialog.show();
        });

        holder.deleteButton.setOnClickListener(v -> {
            discountController.deleteDiscount(discount.getId(), new DiscountController.OnDiscountOperationListener() {
                @Override
                public void onSuccess(Discount discountResult) {
                    int pos = discounts.indexOf(discount);
                    if (pos != -1) {
                        discounts.remove(pos);
                        notifyItemRemoved(pos);
                    }
                    Toast.makeText(context, "Descuento eliminado correctamente", Toast.LENGTH_SHORT).show();
                    if (editListener != null) {
                        editListener.onDiscountDeleted(discount);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(context, "Error al eliminar el descuento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    static class DiscountViewHolder extends RecyclerView.ViewHolder {
        TextView discountCode;
        TextView discountPercentTV;
        Switch discountActiveSwitch;
        Button editButton;
        Button deleteButton;

        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            discountPercentTV = itemView.findViewById(R.id.discountPercentTV);
            discountCode = itemView.findViewById(R.id.discountCode);
            discountActiveSwitch = itemView.findViewById(R.id.discountActiveSwitch);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}