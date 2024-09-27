package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ar.edu.davinci.carbone_lucas.lk_store.R;

public class MyAccountFragment extends Fragment {

    private TextView fragmentTitle;
    private Button viewHistoryButton;
    private Button changePasswordButton;
    private LinearLayout linearButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearButtons = view.findViewById(R.id.linearButtons);
        crearBTNs();
        crearFuncionalidadBTNS();

        ImageView profileImage = view.findViewById(R.id.profileImage);
        Glide.with(this)
                .load(R.drawable.user_profile)
                .circleCrop()
                .into(profileImage);
    }

    private void crearBTNs() {
        // Boton para ver el Historial
        viewHistoryButton = createButton(getString(R.string.ver_historial));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        buttonParams.setMarginEnd(dpToPx(8));
        viewHistoryButton.setLayoutParams(buttonParams);

        // Boton para cambiar la contraseña
        changePasswordButton = createButton(getString(R.string.change_password));
        LinearLayout.LayoutParams button2Params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        button2Params.setMarginStart(dpToPx(8));
        changePasswordButton.setLayoutParams(button2Params);

        // Los agrego al linear
        linearButtons.addView(viewHistoryButton);
        linearButtons.addView(changePasswordButton);
    }

    private Button createButton(String text) {
        Button button = new Button(getContext());
        button.setText(text);
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setTextSize(15);
        button.setBackgroundResource(R.drawable.rounded_button);
        return button;
    }

    private void crearFuncionalidadBTNS() {
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement view history functionality
                Toast.makeText(getContext(), "Ver historial", Toast.LENGTH_SHORT).show();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement change password functionality
                Toast.makeText(getContext(), "Cambiar contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}