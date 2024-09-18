package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView gotoRegister;
    Button gotoHome;
    TextView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView name = findViewById(R.id.nameInput);
        TextView pass = findViewById(R.id.passwordInput);

        alert = new TextView(this);
        alert.setId(View.generateViewId());
        alert.setTextColor(getResources().getColor(R.color.alert));
        alert.setTextSize(12);
        alert.setGravity(View.TEXT_ALIGNMENT_CENTER);
        alert.setVisibility(View.GONE);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        LinearLayout layout = findViewById(R.id.dataContainer_login);
        layoutParams.startToStart = layout.getId();
        layoutParams.endToEnd = layout.getId();

        alert.setLayoutParams(layoutParams);
        layout.addView(alert, 0);

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
                    alert.setVisibility(View.VISIBLE);
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