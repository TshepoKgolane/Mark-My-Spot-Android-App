package com.example.markmyspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Navigation extends AppCompatActivity {
    Button btnNav;
    Button btnHomer;
    EditText spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        btnNav = findViewById(R.id.btnNav);
        spot = findViewById(R.id.txtSpot);
        btnHomer = findViewById(R.id.btnBack);
        btnHomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Navigation.this, mapDisplay.class));
            }
        });
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Navigation.this, "Added Navigation", Toast.LENGTH_SHORT).show();

            }
        });
    }
}