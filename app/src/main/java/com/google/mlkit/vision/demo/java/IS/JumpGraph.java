package com.google.mlkit.vision.demo.java.IS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.mlkit.vision.demo.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JumpGraph extends AppCompatActivity{
    List<Jump> jumps;
    AlertDialog dialog;
    LinearLayout layout;
    String pattern;
    DateFormat df;
    View cardBtnView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_graph);
        jumps = DatabaseManager.getInstance().getJumps(getIntent().getStringExtra("authID"));
        pattern = "dd/MM/yyyy HH:mm";
        df = new SimpleDateFormat(pattern);
        layout = findViewById(R.id.container);
//        buildDialog();
        for(Jump j : jumps){
            String date = df.format(new Date(j.getDate()));
            addCard(Float.toString(j.getHeight()),   "   -   "+date);
        }
    }

//    private void buildDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.dialogue, null);
//        TextView jumpText = view.findViewById(R.id.jumpText);
//        jumpText.setText("By clicking DELETE you'll permanently delete this jump history");
//        builder.setView(view);
//        builder.setTitle("Do you want to delete the selected jump?").setPositiveButton("DELETE", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which){
//                View view = getLayoutInflater().inflate(R.layout.card, null);
//                layout.removeView(view);
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which){
//
//            }
//        });
//        dialog = builder.create();
//    }

    private void addCard(String height, String date){
        View view = getLayoutInflater().inflate(R.layout.card, null);
        TextView heightView = view.findViewById(R.id.height);
        TextView dateView = view.findViewById(R.id.date);
        Button delete = view.findViewById(R.id.delete);
        heightView.setText(height);
        dateView.setText(date);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                layout.removeView(v);
//                dialog.show();
            }
        });
        layout.addView(view);
    }
}