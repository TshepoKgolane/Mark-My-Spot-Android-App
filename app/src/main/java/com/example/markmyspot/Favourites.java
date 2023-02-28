package com.example.markmyspot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Favourites extends AppCompatActivity {
    Button btnAdd;
    EditText latitude;
    EditText longitude;
    EditText name;
    TextView lblerr;

FirebaseAuth mAuth;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("FavLandMarks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        btnAdd = findViewById(R.id.btnAdd);
        latitude = findViewById(R.id.txtLat);
        longitude = findViewById(R.id.txtLong);
        name = findViewById(R.id.txtname);
        lblerr = findViewById(R.id.lblErr);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //userSettings.credentials user1 = new userSettings.credentials();

                    String emal = userSettings.userEmai.myEmail;
                    String longi = longitude.getText().toString();
                    String lat= latitude.getText().toString();
                    String Fname = name.getText().toString();

                    Favour ty = new Favour(emal,lat ,longi ,Fname );
                    root.push().setValue(ty);
                    Toast.makeText(Favourites.this, "Added", Toast.LENGTH_SHORT).show();

                } catch (Exception err) {

                    Toast.makeText(Favourites.this, err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}