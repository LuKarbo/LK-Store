package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    TextView gotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // login_goto
        gotoLogin = findViewById(R.id.login_goto);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cambio a la vista del register
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
}