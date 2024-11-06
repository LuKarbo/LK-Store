package ar.edu.davinci.carbone_lucas.lk_store;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.edu.davinci.carbone_lucas.lk_store.Controllers.UserController;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView alert;
    private EditText nameInput;
    private EditText passwordInput;

    // creo el firebase obj
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inicializo el firebase obj
        mAuth = FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);

        createAlertTextView();
        createLoginButton();
        createAccountMessage();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String password = passwordInput.getText().toString();
                // Log.i("firebase-testing", name);
                // Log.i("firebase-testing", password);

                // Agrego el uso del Auth para el login
                mAuth.signInWithEmailAndPassword(name,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.i("firebase-login", mAuth.getCurrentUser().getEmail());

                                    UserController userController = new UserController();
                                    userController.login(mAuth.getCurrentUser().getUid());

                                    Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intentHome);
                                }
                                else{
                                    alert.setText("Credenciales inválidas");
                                    alert.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });
    }

    // creo el mensaje de Alerta del onCreate para dejarlo más limpio
    private void createAlertTextView() {
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
        LinearLayout layout = findViewById(R.id.dataContainer_login);
        layoutParams.startToStart = layout.getId();
        layoutParams.endToEnd = layout.getId();

        alert.setLayoutParams(layoutParams);
        layout.addView(alert, 0);
    }

    // creo el Boton de Login fuera del onCreate para dejarlo más limpio
    private void createLoginButton() {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(ContextCompat.getColor(this, R.color.gotoRegisterLogin));
        shape.setCornerRadius(dpToPx(30));

        loginButton = new Button(this);
        loginButton.setId(View.generateViewId());
        loginButton.setText(R.string.ingresar);
        loginButton.setTextColor(ContextCompat.getColor(this, R.color.white));
        loginButton.setTextSize(17);
        loginButton.setBackground(shape);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                dpToPx(320),
                dpToPx(60)
        );
        layoutParams.topToBottom = R.id.passwordInput;
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topMargin = dpToPx(16);

        LinearLayout layout = findViewById(R.id.dataContainer_login);
        loginButton.setLayoutParams(layoutParams);
        layout.addView(loginButton);
    }

    // creo el mensaje fuera del onCreate para dejarlo más limpio
    private void createAccountMessage() {
        LinearLayout containerMsg = new LinearLayout(this);
        containerMsg.setId(View.generateViewId());
        containerMsg.setOrientation(LinearLayout.HORIZONTAL);
        containerMsg.setGravity(android.view.Gravity.CENTER);

        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        containerParams.topMargin = dpToPx(16);
        containerMsg.setLayoutParams(containerParams);

        TextView haveAccountMsg = new TextView(this);
        haveAccountMsg.setText(R.string.notHaveAccount);
        haveAccountMsg.setTextColor(ContextCompat.getColor(this, R.color.white));

        TextView registerGoto = new TextView(this);
        registerGoto.setText(R.string.notHaveAccount_goto);
        registerGoto.setTextColor(ContextCompat.getColor(this, R.color.gotoRegisterLogin));
        LinearLayout.LayoutParams registerGotoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        registerGotoParams.leftMargin = dpToPx(5);
        registerGoto.setLayoutParams(registerGotoParams);

        containerMsg.addView(haveAccountMsg);
        containerMsg.addView(registerGoto);

        LinearLayout dataContainer = findViewById(R.id.dataContainer_login);
        dataContainer.addView(containerMsg);

        registerGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}