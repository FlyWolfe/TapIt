package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SendInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_info);

        Button btnSendInfo = (Button)findViewById(R.id.buttonSendInfo);
        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButton_ReturnToMain);

        btnSendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendInfo.this, WaitingToSend.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendInfo.this, MainActivity.class));
            }
        });

    }
}
