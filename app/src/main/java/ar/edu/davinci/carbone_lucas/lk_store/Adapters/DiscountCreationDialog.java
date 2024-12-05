package ar.edu.davinci.carbone_lucas.lk_store.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.DiscountController;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Discount.Discount;

public class DiscountCreationDialog extends Dialog {
    private EditText percentEditText;
    private EditText codeEditText;
    private Switch activeSwitch;
    private Button saveButton;
    private Button cancelButton;
    private DiscountController discountController;
    private Discount existingDiscount;
    private OnDiscountSavedListener listener;

    public interface OnDiscountSavedListener {
        void onDiscountSaved();
    }

    public DiscountCreationDialog(@NonNull Context context, DiscountController discountController,
                                  OnDiscountSavedListener listener) {
        super(context);
        this.discountController = discountController;
        this.listener = listener;
    }

    public DiscountCreationDialog(@NonNull Context context, DiscountController discountController,
                                  Discount discount, OnDiscountSavedListener listener) {
        this(context, discountController, listener);
        this.existingDiscount = discount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_discount_creation);
        setTitle(existingDiscount == null ? "Crear Descuento" : "Editar Descuento");

        percentEditText = findViewById(R.id.percentEditText);
        codeEditText = findViewById(R.id.codeEditText);
        activeSwitch = findViewById(R.id.activeSwitch);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        if (existingDiscount != null) {
            percentEditText.setText(String.valueOf(existingDiscount.getPercent()));
            activeSwitch.setChecked(existingDiscount.isActive());
        }

        saveButton.setOnClickListener(v -> saveDiscount());
        cancelButton.setOnClickListener(v -> dismiss());
    }

    private void saveDiscount() {
        String percentStr = percentEditText.getText().toString().trim();
        String codeStr = codeEditText.getText().toString().trim();

        if (TextUtils.isEmpty(codeStr)) {
            Toast.makeText(getContext(), "Por favor ingrese un Codigo/nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(percentStr)) {
            Toast.makeText(getContext(), "Por favor ingrese un porcentaje", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double percent = Double.parseDouble(percentStr);

            if (percent < 0 || percent > 100) {
                Toast.makeText(getContext(), "El porcentaje debe estar entre 0 y 100", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isActive = activeSwitch.isChecked();

            if (existingDiscount == null) {
                discountController.createDiscount(codeStr, percent, isActive, new DiscountController.OnDiscountOperationListener() {
                    @Override
                    public void onSuccess(Discount discount) {
                        Toast.makeText(getContext(), "Descuento creado exitosamente", Toast.LENGTH_SHORT).show();
                        if (listener != null) listener.onDiscountSaved();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), "Error al crear descuento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                discountController.updateDiscount(
                        existingDiscount.getId(),
                        codeStr,
                        percent,
                        isActive,
                        new DiscountController.OnDiscountOperationListener() {
                            @Override
                            public void onSuccess(Discount discount) {
                                Toast.makeText(getContext(), "Descuento actualizado exitosamente", Toast.LENGTH_SHORT).show();
                                if (listener != null) listener.onDiscountSaved();
                                dismiss();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getContext(), "Error al actualizar descuento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Por favor ingrese un número válido", Toast.LENGTH_SHORT).show();
        }
    }
}
