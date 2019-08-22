package com.example.wjddu.layout1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ct1, ct2, ct3, ct4, ct5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ct1 = findViewById(R.id.ct1);
        ct2 = findViewById(R.id.ct2);
        ct3 = findViewById(R.id.ct3);
        ct4 = findViewById(R.id.ct4);
        ct5 = findViewById(R.id.ct5);

        ct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fvintent = new Intent(getApplicationContext(), fgreet.class);
                startActivity(fvintent);
            }
        });

        ct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent askintent = new Intent(getApplicationContext(), fgreet.class);
                startActivity(askintent);
            }
        });

        ct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fvintent = new Intent(getApplicationContext(), fgreet.class);
                startActivity(fvintent);
            }
        });

        ct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fvintent = new Intent(getApplicationContext(), fgreet.class);
                startActivity(fvintent);
            }
        });

        ct5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fvintent = new Intent(getApplicationContext(), fgreet.class);
                startActivity(fvintent);
            }
        });


    }
}
