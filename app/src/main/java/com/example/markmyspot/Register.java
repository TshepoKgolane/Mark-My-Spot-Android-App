package com.example.markmyspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText name;
    EditText surname;
    EditText RUsername;
    EditText RPassword;
    Button btnregister;


    //string attributes
    String savedName;
    String savedSurname;
    String savedUsername;
    String savedPassword;
    FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            myAuth = FirebaseAuth.getInstance();

            name=(EditText) findViewById(R.id.name);
            surname=(EditText) findViewById(R.id.surname);
            RUsername=(EditText) findViewById(R.id.Rusername);
            RPassword=(EditText) findViewById(R.id.Rpassword);
            btnregister =(Button) findViewById(R.id.btnRegister);


            //register button
            btnregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (name.length()==0){
                        name.setError("Your name is Mandatory");
                        name.requestFocus();
                        }else  if (surname.length()==0){
                        surname.setError("Your surname is Mandatory");
                        surname.requestFocus();
                    }else  if (RUsername.length()==0){
                        RUsername.setError("Email cannot be empty");
                        RUsername.requestFocus();
                        }else  if (RPassword.length()==0){
                        RPassword.setError("Password cannot be empty");
                        RPassword.requestFocus();
                    }else{
                        createUser();
                        //registered
                        savedName = name.getText().toString();
                        savedSurname = surname.getText().toString();
                        savedUsername = RUsername.getText().toString();
                        savedPassword = RPassword.getText().toString();
                        //go to

                       // Snackbar.make(findViewById(android.R.id.content),"Successfully created account", Snackbar.LENGTH_LONG).show();
                       // openIntent(Register.this,RUsername.getText().toString(),RPassword.getText().toString(),Login.class);
                    }

                }
            });
        }catch (Exception e){
            Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void createUser() {
        savedUsername = RUsername.getText().toString();
        savedPassword = RPassword.getText().toString();
    myAuth.createUserWithEmailAndPassword(savedUsername,savedPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Toast.makeText(Register.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this,Login.class));
            }else{
                Toast.makeText(Register.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    });
    }

    public  void openIntent(Context cont, String username, String Password, Class next){
        Intent inte = new Intent(cont,next);
        inte.putExtra("username",username);
        inte.putExtra("Password",Password);
        cont.startActivity(inte);
    }
    public String getSavedPassword() {
        return savedPassword;
    };

    public String getSavedEmail() {
        return savedUsername;
    }
    public String getSavedName() {
        return savedName;
    }

    public String getSavedSurname() {
        return savedSurname;
    }
}
