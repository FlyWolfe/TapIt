package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InfoReceived extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_received);

        Button btnAdd = (Button)findViewById(R.id.buttonAddPerson);
        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButton_ReturnToReceive);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoReceived.this, MainActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoReceived.this, ReceiveInfo.class));
            }
        });
    }
}
