package com.google.mlkit.vision.demo.java.IS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.java.ChooserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private boolean flag = false;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button loginBtn = this.<Button>findViewById(R.id.loginBtn);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        // open DB
        DatabaseManager.getInstance().openDatabase(this);
        editTextEmail.setText("admin");
        editTextPassword.setText("admin");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if ((email.equals("admin")) && (password.equals("admin"))) {

//                    for(int i = 0; i < 10; i++){
//                        Jump j = new Jump(0, i, System.currentTimeMillis());
//                        DatabaseManager.getInstance().createJump(j);
//                    }
//                    User user = new User(9999, "admin", "admin@admin.com", "admin");
//                    DatabaseManager.getInstance().createUser(user);
                    for(Jump j : DatabaseManager.getInstance().getAllJumps(1))
                        System.out.println(j);
                    Toast.makeText(getApplicationContext(), "Hello Admin", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
                    startActivity(intent);
                } else {
                    // validate user using firebase
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(MainActivity.this, "Hello, "+user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                flag=true;
                                Toast.makeText(getApplicationContext(), "Hello "+ user.getDisplayName(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Sorry, your password was incorrect.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    if(flag)
                        Toast.makeText(getApplicationContext(), "Please check the email and password again", Toast.LENGTH_LONG).show();
                }
            }

        });

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserRegister.class);
                startActivity(intent);
            }
        });
    }

    public void updateUI(FirebaseUser user){
        if(user != null){
//            DataBaseManager.getInstance().openDataBase(this);
//            Intent intent = new Intent(this, MainPage.class);
//            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseManager.getInstance().closeDatabase();
    }
}