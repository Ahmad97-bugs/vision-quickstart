package com.google.mlkit.vision.demo.java.IS;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.mlkit.vision.demo.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Jump_History extends AppCompatActivity{
    List<Jump> jumps;
    private FirebaseAuth mAuth;
    LinearLayout layout;
    String pattern;
    DateFormat df;
    Button deleteAll;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_graph);
        mAuth = FirebaseAuth.getInstance();
        jumps = DatabaseManager.getInstance().getJumps(getIntent().getStringExtra("authID"));
        pattern = "dd/MM/yyyy HH:mm";
        df = new SimpleDateFormat(pattern);
        layout = findViewById(R.id.container);
        for(Jump j : jumps){
            addCard(j);
        }
        Button dltAll = (Button) findViewById(R.id.deleteAll);
        dltAll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Firebase.getInstance().deleteAllJumps(mAuth.getUid());
                startActivity(new Intent(Jump_History.this, Jump_History.class));
            }
        });
    }



    private void addCard(Jump j){
        View view = getLayoutInflater().inflate(R.layout.card, null);
        TextView heightView = view.findViewById(R.id.height);
        TextView dateView = view.findViewById(R.id.date);
        Button delete = view.findViewById(R.id.delete);
        String height = Float.toString(j.getHeight());
        String date = df.format(new Date(j.getDate()));
        heightView.setText(height);
        dateView.setText("   -   " + date);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                layout.removeView(view);
                Firebase.getInstance().deleteJump(j, mAuth.getUid());
                jumps.remove(j);
                DatabaseManager.getInstance().setJumps(jumps);
//                dialog.show();
            }
        });
        layout.addView(view);
    }
}