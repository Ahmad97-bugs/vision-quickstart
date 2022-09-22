package com.google.mlkit.vision.demo.java.GUI;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.mlkit.vision.demo.R;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private boolean flag = false;
//    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}