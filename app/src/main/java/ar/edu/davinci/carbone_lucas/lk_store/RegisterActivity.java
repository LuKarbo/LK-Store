package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.UserController;

public class RegisterActivity extends AppCompatActivity {

    TextView gotoLogin;
    Button regiserBTN;
    EditText emailInput;
    EditText nameInput;
    EditText passwordInput;
    EditText passwordConfirmdInput;
    TextView alert;

    // creo el firebase obj
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inicializo el firebase obj
        mAuth = FirebaseAuth.getInstance();

        // login_goto
        gotoLogin = findViewById(R.id.register_goto);

        emailInput = findViewById(R.id.emailInput);
        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordConfirmdInput = findViewById(R.id.passwordConfirmdInput);

        alert = new TextView(this);
        alert.setId(View.generateViewId());
        alert.setTextColor(getResources().getColor(R.color.alert));
        alert.setTextSize(12);
        alert.setGravity(View.TEXT_ALIGNMENT_CENTER);
        alert.setVisibility(View.GONE);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout layout = findViewById(R.id.dataContainer_register);
        layoutParams.startToStart = layout.getId();
        layoutParams.endToEnd = layout.getId();

        alert.setLayoutParams(layoutParams);
        layout.addView(alert, 0);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambio a la vista del register
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        // regiser btn
        regiserBTN = findViewById(R.id.registerBTN);

        regiserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!passwordInput.getText().toString().equals(passwordConfirmdInput.getText().toString())){
                    alert.setText("Las contraseñas deben ser iguales");
                    alert.setVisibility(View.VISIBLE);
                }
                else if (nameInput.getText().toString().equals("")) {
                    alert.setText("Nombre no válido");
                    alert.setVisibility(View.VISIBLE);
                }
                else if (emailInput.getText().toString().equals("")) {
                    alert.setText("Email no válido");
                    alert.setVisibility(View.VISIBLE);
                } else {
                    mAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userId = task.getResult().getUser().getUid();

                                        UserController uc = new UserController();
                                        uc.register(
                                                userId,
                                                nameInput.getText().toString(),
                                                emailInput.getText().toString(),
                                                passwordInput.getText().toString()
                                        );

                                        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intentLogin);
                                    } else {
                                        alert.setText("Error al registrarte");
                                        alert.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }
            }
        });
    }
}