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
                Intent intent1 = new Intent(getApplicationContext(), fgreet.class);
                startActivity(intent1);
            }
        });

        ct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), vlearn.class);
                startActivity(intent2);
            }
        });

        ct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), apolo.class);
                startActivity(intent3);
            }
        });

        ct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(getApplicationContext(), fight.class);
                startActivity(intent4);
            }
        });

        ct5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(), love.class);
                startActivity(intent5);
            }
        });


    }
}
