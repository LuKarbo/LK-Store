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
import ar.edu.davinci.carbone_lucas.lk_store.models.User;

public class MyAccountFragment extends Fragment {

    private TextView userNameText;
    private TextView userEmailText;
    private TextView userPhoneText;
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

        // Inicializar vistas
        linearButtons = view.findViewById(R.id.linearButtons);
        userNameText = view.findViewById(R.id.userName);
        userEmailText = view.findViewById(R.id.userEmail);
        userPhoneText = view.findViewById(R.id.userPhone);

        // Obtener y mostrar la información del usuario
        loadUserInfo();

        crearBTNs();
        crearFuncionalidadBTNS();

        // Cargar imagen de perfil
        ImageView profileImage = view.findViewById(R.id.profileImage);
        Glide.with(this)
                .load(R.drawable.user_profile)
                .circleCrop()
                .into(profileImage);
    }

    private void loadUserInfo() {
        User currentUser = User.getInstance();
        if (currentUser != null) {
            userNameText.setText(currentUser.getName());
            userEmailText.setText(currentUser.getEmail());
            userPhoneText.setText(currentUser.getPhoneNumber());
        }
    }

    private void crearBTNs() {
        // Botón para ver el Historial
        viewHistoryButton = createButton(getString(R.string.ver_historial));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        buttonParams.setMarginEnd(dpToPx(8));
        viewHistoryButton.setLayoutParams(buttonParams);

        // Botón para cambiar la contraseña
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
                Toast.makeText(getContext(), "Ver historial", Toast.LENGTH_SHORT).show();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí podrías implementar la funcionalidad de cambio de contraseña
                // usando el password actual guardado en User.getInstance().getPassword()
                Toast.makeText(getContext(), "Cambiar contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}