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

/**
 * activity to register new user to system
 * name field
 * email field
 * password and confirm password field
 * mAuth to create authentication for user to log in
 * REmail register email - confirm not empty field
 * RPassword register password - confirm not empty field
 * RName register name - confirm not empty field
 * RcPassword  register confirmed password - confirm not empty field
 */
public class UserRegister extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView password;
    private TextView cPassword;
    private FirebaseAuth mAuth;
    private String REmail;
    private String RPassword;
    private String RName;
    private String RcPassword;
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
        cPassword = findViewById(R.id.secondPassword);
        Button save = findViewById(R.id.saveBtn);
        //button to validate fields and confirm new user authentication
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REmail = email.getText().toString();
                RPassword = password.getText().toString();
                RName = name.getText().toString();
                RcPassword = cPassword.getText().toString();
                if (!RName.equals("")) {
                    if (!REmail.equals("")) {
                        if (!RPassword.equals("")) {
                           if(RcPassword.equals(RPassword)) {
                                mAuth.fetchSignInMethodsForEmail(REmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task){
                                        boolean check = ! task.getResult().getSignInMethods().isEmpty();
                                        if(! check){
                                            if(REmail != null && RPassword != null){
                                                mAuth.createUserWithEmailAndPassword(REmail, RPassword).addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>(){
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task){
                                                        if(task.isSuccessful()){
                                                            User user = new User(mAuth.getUid(), name.getText().toString(), email.getText().toString(), password.getText().toString());
                                                            DatabaseManager.getInstance().createUser(user);
                                                            Snackbar snackbar = Snackbar
                                                                    .make(v, "User Registered", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                            Intent intent = new Intent(UserRegister.this, ChooserActivity.class);
                                                            startActivity(intent);
                                                        } else{
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
                            } else{
                               Toast.makeText(ctx, "Your password does not match", Toast.LENGTH_LONG).show();
                           }
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