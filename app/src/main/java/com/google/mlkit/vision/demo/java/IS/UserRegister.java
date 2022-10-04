package com.google.mlkit.vision.demo.java.IS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.java.ChooserActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserRegister extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView password;
    private TextView date;
    private FirebaseAuth mAuth;
    private String REmail;
    private String RPassword;
    private String RName;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Register");
        ab.setDisplayHomeAsUpEnabled(true);
        ctx = this;
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.userTxt);
        email = findViewById(R.id.mailTxt);
        password = findViewById(R.id.passTxt);
        date = findViewById(R.id.textDate);
        Button save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REmail = email.getText().toString();
                RPassword = password.getText().toString();
                RName = name.getText().toString();
                if (!RName.equals("")) {
                    if (!REmail.equals("")) {
                        if (!RPassword.equals("")) {
                                mAuth.fetchSignInMethodsForEmail(REmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                                        if(!check){
                                            if (REmail != null && RPassword != null) {
                                                mAuth.createUserWithEmailAndPassword(REmail, RPassword).addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            User user = new User(mAuth.getUid(), name.getText().toString(), email.getText().toString(), password.getText().toString());
                                                            DatabaseManager.getInstance().createUser(user);
                                                            Snackbar snackbar = Snackbar
                                                                    .make(v, "User Registered", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                            Intent intent = new Intent(UserRegister.this, ChooserActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Toast.makeText(UserRegister.this, task.getException().toString(), Toast.LENGTH_LONG);
                                                        }
                                                    }
                                                });
                                            }
                                        } else{
                                            Toast.makeText(UserRegister.this, "Email already in use", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        } else {
                            Toast.makeText(ctx, "Password field is empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ctx, "E-mail field is empty", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ctx, "Name field is empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}