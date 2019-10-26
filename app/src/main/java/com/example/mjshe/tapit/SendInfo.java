package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SendInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_info);

        final CheckBox checkboxFB = (CheckBox)findViewById(R.id.checkBoxFB);
        final CheckBox checkboxEM = (CheckBox)findViewById(R.id.checkBoxEM);
        final CheckBox checkboxPH = (CheckBox)findViewById(R.id.checkBoxPH);
        final CheckBox checkboxTW = (CheckBox)findViewById(R.id.checkBoxTW);
        final CheckBox checkboxYT = (CheckBox)findViewById(R.id.checkBoxYT);
        final CheckBox checkboxIN = (CheckBox)findViewById(R.id.checkBoxIN);

        Button btnSendInfo = (Button)findViewById(R.id.buttonSendInfo);
        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButton_ReturnToMain);

        btnSendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendInfo.this, WaitingToSend.class);

                String textToWrite = "Tap it:\n\n";
                if (checkboxFB.isChecked()) {
                    textToWrite += "Facebook\n\n";
                }
                if (checkboxEM.isChecked()) {
                    textToWrite += "Email\n\n";
                }
                if (checkboxPH.isChecked()) {
                    textToWrite += "Phone\n\n";
                }
                if (checkboxTW.isChecked()) {
                    textToWrite += "Twitter\n\n";
                }
                if (checkboxYT.isChecked()) {
                    textToWrite += "Youtube\n\n";
                }
                if (checkboxIN.isChecked()) {
                    textToWrite += "Instagram\n\n";
                }


                intent.putExtra("SOCIAL_MEDIAS", textToWrite);
                startActivity(intent);
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
