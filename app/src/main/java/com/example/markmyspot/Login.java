package com.example.markmyspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    String username2;
    String AdminEmail = "admin";
    String password2;
    String AdminPassword = "123";
    TextView noAcc;

    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();


        //initialisation
        username = findViewById(R.id.Loginusername);
        password = findViewById(R.id.Loginpassword);
        noAcc = findViewById(R.id.NoAcc);
        //direct to register activity
        noAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mAuth = FirebaseAuth.getInstance();
                    loginAuthentication();
                } catch (NullPointerException rig) {
                    Snackbar.make(findViewById(android.R.id.content), "You do not have an account yet\nPlease create an account", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Snackbar.make(findViewById(android.R.id.content), "" + e.getMessage().trim(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void loginAuthentication() {
        try {

            if (username.length() == 0 || password.length() == 0) {
                username.setError("Requirred");
                password.setError("Requirred");
                password.requestFocus();
                username.requestFocus();

            } else {

                username2 = username.getText().toString();
                password2 = password.getText().toString();
                mAuth.signInWithEmailAndPassword(username2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Favour mu = new Favour();
                           // mu.setEmail(username2);
                            userSettings.userEmai.myEmail=username2;
                            startActivity(new Intent(Login.this, mapDisplay.class));
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "Invalid credentials:", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        } catch (Exception err) {
            Toast.makeText(this, "Error\n" + err.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}