package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WaitingToSend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_to_send);

        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButtonBackToSend);

        TextView textview = (TextView)findViewById(R.id.textView4);

        textview.setText(getIntent().getStringExtra("SOCIAL_MEDIAS"));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitingToSend.this, SendInfo.class));
            }
        });

    }
}
