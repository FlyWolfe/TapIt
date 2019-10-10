package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WaitingToSend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_to_send);
        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButtonBackToSend);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitingToSend.this, SendInfo.class));
            }
        });

    }
}
