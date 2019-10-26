package com.example.mjshe.tapit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


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

import java.nio.charset.Charset;
import java.util.ArrayList;

public class WaitingToSend extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback, NfcAdapter.CreateNdefMessageCallback{

    private static String TAG = "NFCDEMO:"+MainActivity.class.getSimpleName();

    private NfcAdapter nfcAdapter;

    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    //private ArrayList<String> messagesReceivedArray = new ArrayList<>();


    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //This will be called when another NFC capable device is detected.
        if (messagesToSendArray.size() == 0) {
            return null;
        }
        //We'll write the createRecords() method in just a moment
        NdefRecord[] recordsToAttach = createRecords();


        Log.i("NFC", "Created Message!");
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        //This is called when the system detects that our NdefMessage was
        //Successfully sent.

        Log.i("NFC", "Message Sent!");
        TextView textview = (TextView)findViewById(R.id.textView4);

        textview.setText("SENT MESSAGE");
        messagesToSendArray.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_to_send);

        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.floatingActionButtonBackToSend);

        TextView textview = (TextView)findViewById(R.id.textView4);

        textview.setText(getIntent().getStringExtra("SOCIAL_MEDIAS"));

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter !=null && nfcAdapter.isEnabled())
        {
            //This will refer back to createNdefMessage for what it will send
            nfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
            Log.i("NFC", "NFC Adapter is enabled!");
        }
        else
        {
            Log.i("NFC", "NFC not available!");
            finish();
        }

        // Register callback
        nfcAdapter.setNdefPushMessageCallback(_onNfcCreateCallback, this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitingToSend.this, SendInfo.class));
            }
        });

    }

    private NfcAdapter.CreateNdefMessageCallback _onNfcCreateCallback = new NfcAdapter.CreateNdefMessageCallback() {
        @Override
        public NdefMessage createNdefMessage(NfcEvent inputNfcEvent) {
            Log.i(TAG, "createNdefMessage");
            return createMessage();
        }
    };

    private NdefMessage createMessage() {
        String text = ("Hello there from another device!\n\n" +
                "Beam Time: " + System.currentTimeMillis());
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { NdefRecord.createMime(
                        "application/com.bluefletch.nfcdemo.mimetype", text.getBytes())
                        /**
                         * The Android Application Record (AAR) is commented out. When a device
                         * receives a push with an AAR in it, the application specified in the AAR
                         * is guaranteed to run. The AAR overrides the tag dispatch system.
                         * You can add it back in to guarantee that this
                         * activity starts when receiving a beamed message. For now, this code
                         * uses the tag dispatch system.
                        */
                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
                });
        return msg;
    }

    public NdefRecord[] createRecords() {

        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
        {
            for (int i = 0; i < messagesToSendArray.size(); i++) {

                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = new NdefRecord(
                        NdefRecord.TNF_WELL_KNOWN,  //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,        //Description of our payload
                        new byte[0],                //The optional id for our Record
                        payload);                   //Our payload for the Record

                records[i] = record;
            }

            Log.i("NFC", "Records Created, JELLY BEAN!");
        }

        else{
            for(int i = 0; i < messagesToSendArray.size(); i++)
            {
                byte[] payload = messagesToSendArray.get(i).getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain",payload);
                records[i] = record;
            }

            Log.i("NFC", "Records Created, NORMAL!");
        }
        //Remember to change the size of your array when you instantiate it.
        records[messagesToSendArray.size()] =
                NdefRecord.createApplicationRecord(getPackageName());
        return records;
    }
}
