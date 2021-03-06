package com.wadimkazak.cloudchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Wadim on 04.06.2018.
 */

public class ValidationActivity extends AppCompatActivity {
    Button btnRegistration, btnSign;
    EditText edtName, edtPass;
    CheckBox cbRememberMe;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    final static String EMAIL = "EMAIL";
    final static String PASS = "PASS";
    SharedPreferences preferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validation_layout);

        preferences = getPreferences(MODE_PRIVATE);



        if (preferences.getString(EMAIL, null) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegistration = findViewById(R.id.btnReg);
        btnSign = findViewById(R.id.btnSign);
        edtName = findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPass);
        cbRememberMe = findViewById(R.id.chRememberMe);

        firebaseAuth = FirebaseAuth.getInstance();


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtName.getText().toString(), edtPass.getText().toString());
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(edtName.getText().toString(), edtPass.getText().toString());
            }
        });


    }

    private void signIn(String name, String pass) {
        firebaseAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ValidationActivity.this, "Complete sign", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void registration(String name, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(name, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ValidationActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                    if (cbRememberMe.isChecked()) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(EMAIL, edtName.getText().toString());
                        editor.putString(PASS, edtPass.getText().toString());
                        editor.commit();
                        Toast.makeText(ValidationActivity.this, "Registration 111", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ValidationActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ValidationActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
