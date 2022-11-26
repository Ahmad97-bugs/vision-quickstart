package com.google.mlkit.vision.demo.java.IS;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.demo.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * intent to show the user examples on how to jump
 */
public class Instructions extends AppCompatActivity{
    ImageView stand;
    ImageView down;
    ImageView jump;
    Button next;
    Button back;
    TextView ins;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ins = findViewById(R.id.instuctions);
        ins.setText("Stand with your arms next to your body");

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
            ins.setText("Stand with your arms next to your body");
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            jump.setVisibility(View.INVISIBLE);
            stand.setVisibility(View.VISIBLE);
        } else if(count == 1){
            ins.setText("Getting into squat position with your hands moving with your hips");
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            down.setVisibility(View.VISIBLE);
            jump.setVisibility(View.INVISIBLE);
            stand.setVisibility(View.INVISIBLE);
        } else if(count == 2){
            ins.setText("Jump as you lift your arms into the air");
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