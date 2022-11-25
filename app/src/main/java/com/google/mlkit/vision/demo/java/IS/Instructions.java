package com.google.mlkit.vision.demo.java.IS;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.demo.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Instructions extends AppCompatActivity{
    ImageView stand;
    ImageView down;
    ImageView jump;
    Button next;
    Button back;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        stand = findViewById(R.id.stand);
        down = findViewById(R.id.down);
        jump = findViewById(R.id.jump);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        back.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);
        jump.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                count--;
                updatePicture();
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                count++;
                updatePicture();
            }
        });
    }

    private void updatePicture(){
        if(count == 0){
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            jump.setVisibility(View.INVISIBLE);
            stand.setVisibility(View.VISIBLE);
        } else if(count == 1){
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            down.setVisibility(View.VISIBLE);
            jump.setVisibility(View.INVISIBLE);
            stand.setVisibility(View.INVISIBLE);
        } else if(count == 2){
            next.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            down.setVisibility(View.INVISIBLE);
            jump.setVisibility(View.VISIBLE);
            stand.setVisibility(View.INVISIBLE);
        } else{
            count = 0;
            updatePicture();
        }
    }
}