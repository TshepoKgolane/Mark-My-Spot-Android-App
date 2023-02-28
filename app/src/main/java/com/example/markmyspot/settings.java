package com.example.markmyspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class settings extends AppCompatActivity {
    AutoCompleteTextView Atxt;
    ArrayList<String> categoriesArray = new ArrayList<String>();
    ArrayAdapter<String> adapterItems;
    String selected = "";
    //initialisations
    Button btnSave;
    CheckBox check1;
    CheckBox check2;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("UserSettings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Atxt = findViewById(R.id.dropDownItems);
        btnSave = findViewById(R.id.btnSave);
        categoriesArray.add("Historical");
        categoriesArray.add("Modern");
        categoriesArray.add("Popular");
        //checkboxes
        check1 = findViewById(R.id.checkBox1);
        check2 = findViewById(R.id.checkBox2);
        try {
            adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_items, categoriesArray);
            Atxt.setAdapter(adapterItems);
            Atxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selected = adapterView.getItemAtPosition(i).toString();

                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userSettings.credentials obj = new userSettings.credentials();
                    try {
                        if (check1.isChecked() == true) {
                            //metric
                            userSettings sett = new userSettings(userSettings.userEmai.myEmail, selected, "Metric");
                            root.push().setValue(sett);
                            Toast.makeText(settings.this, "Added settings to database", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(settings.this, mapDisplay.class));

                        } else if (check2.isChecked() == true) {
                            //imperial
                            userSettings sett = new userSettings(obj.getDummyEmail(), selected, "Imperial");
                            root.push().setValue(sett);
                            Toast.makeText(settings.this, "Added settings to database", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(settings.this, mapDisplay.class));
                        } else {
                            Toast.makeText(settings.this, "Select Measurement system", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ty) {
                        Toast.makeText(settings.this, "" + ty.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception err) {
            Toast.makeText(this, "" + err.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}