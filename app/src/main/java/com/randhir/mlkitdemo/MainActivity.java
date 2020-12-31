package com.randhir.mlkitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button textD;

    private Button faceD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textD = (Button) findViewById(R.id.textD);

        faceD = (Button) findViewById(R.id.faceD);

        textD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , TextDActivity.class);
                startActivity(intent);

            }
        });

        faceD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this , FaceDActivity.class);
                startActivity(intent);
            }
        });
    }
}