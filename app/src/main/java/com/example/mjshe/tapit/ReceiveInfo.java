package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceiveInfo extends AppCompatActivity {

    private NfcAdapter nfcAdapter;

    private ArrayList<String> messagesReceivedArray = new ArrayList<>();

    private void handleNfcIntent(Intent NfcIntent)
    {
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray = NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null) {

                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage)receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for(NdefRecord record:attachedRecords){
                    String string = new  String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())){ continue;}
                    messagesReceivedArray.add(string);
                }
                Toast.makeText(this, "Recieved " + messagesReceivedArray.size() + " Messages", Toast.LENGTH_LONG).show();
                //updateTextViews();
            }
            else {
                Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void processNFCData( Intent inputIntent ) {

        Parcelable[] rawMessages =
                inputIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (rawMessages != null && rawMessages.length > 0) {

            NdefMessage[] messages = new NdefMessage[rawMessages.length];

            for (int i = 0; i < rawMessages.length; i++) {

                messages[i] = (NdefMessage) rawMessages[i];

            }
            Log.i("NFC", "message size = " + messages.length);

            // only one message sent during the Android beam
            // so you can just grab the first record.
            NdefMessage msg = (NdefMessage) rawMessages[0];

            // record 0 contains the MIME type, record 1 is the AAR, if present
            String payloadStringData = new String(msg.getRecords()[0].getPayload());

            // now do something with your payload payloadStringData
            TextView textview = (TextView)findViewById(R.id.textView);

            textview.setText(payloadStringData);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals( intent.getAction() )) {
            // We scanned an NFC Tag.
            processNFCData( intent );
        }

        //handleNfcIntent(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_info);

        //

        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButton_ReturnToMainFromReceive);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceiveInfo.this, MainActivity.class));
            }
        });

        //handleNfcIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals( getIntent().getAction() )) {
            // Yes, Activity start via Beam...  wonder if we should pass a flag indicating Beam?
            processNFCData( getIntent() );
        }
    }

}
