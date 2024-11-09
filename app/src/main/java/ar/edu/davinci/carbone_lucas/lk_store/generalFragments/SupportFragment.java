package ar.edu.davinci.carbone_lucas.lk_store.generalFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.UUID;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.SupportController;
import ar.edu.davinci.carbone_lucas.lk_store.R;
import ar.edu.davinci.carbone_lucas.lk_store.models.Support;
import ar.edu.davinci.carbone_lucas.lk_store.models.User;


public class SupportFragment extends Fragment {
    private EditText emailEditText;
    private EditText consultaEditText;
    private Button enviarButton;
    private Button verConsultasButton;
    private ProgressBar progressBar;

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        consultaEditText = view.findViewById(R.id.consultaEditText);
        enviarButton = view.findViewById(R.id.enviarButton);
        verConsultasButton = view.findViewById(R.id.verConsultasButton);
        progressBar = view.findViewById(R.id.progressBar);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String consulta = consultaEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("El email es requerido");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Ingrese un email válido");
                    return;
                }

                if (consulta.isEmpty()) {
                    consultaEditText.setError("La consulta es requerida");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                enviarButton.setEnabled(false);

                SupportController sc = new SupportController();
                sc.enviarConsulta("1", User.getInstance().getUserId(), email, consulta);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        enviarButton.setEnabled(true);
                        limpiarFormulario();
                        Toast.makeText(getContext(), "Consulta Enviada!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });

        verConsultasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        return view;
    }

    private void limpiarFormulario() {
        emailEditText.setText("");
        consultaEditText.setText("");
    }

}