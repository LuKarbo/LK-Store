package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView gotoRegister;

    Button gotoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView name = findViewById(R.id.nameInput);
        TextView pass = findViewById(R.id.passwordInput);
        TextView alert = findViewById(R.id.alertTextView);

        gotoHome = findViewById(R.id.loginBTN);

        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validaciones para ver si es un usuario cargado
                // etc
                // etc

                if(name.getText().toString().equals("test") && pass.getText().toString().equals("123"))
                {
                    // aviso de fallo
                    alert.setText("Credenciales inválidas");
                }
                else{
                    // cambio a la vista del register
                    Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentHome);
                }
            }
        });

        gotoRegister = findViewById(R.id.register_goto);

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambio a la vista del register
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

    }
}