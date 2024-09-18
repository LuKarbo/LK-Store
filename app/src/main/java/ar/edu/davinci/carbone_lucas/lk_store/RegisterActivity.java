package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegisterActivity extends AppCompatActivity {

    TextView gotoLogin;
    Button regiserBTN;
    EditText nameInput;
    TextView alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // login_goto
        gotoLogin = findViewById(R.id.register_goto);

        nameInput = findViewById(R.id.nameInput);

        alert = new TextView(this);
        alert.setId(View.generateViewId());
        alert.setTextColor(getResources().getColor(R.color.alert)); // Usa tu color de alerta
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
                if (nameInput.getText().toString().equals("test")) {
                    // Mostrar el mensaje de alerta
                    alert.setText("Nombre no válido");
                    alert.setVisibility(View.VISIBLE);
                } else {
                    Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                }
            }
        });


    }
}