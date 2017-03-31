package com.adithyasairam.chattr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ai.wit.sdk.IWitListener;
import ai.wit.sdk.Wit;
import ai.wit.sdk.model.WitOutcome;

public class MainActivity extends AppCompatActivity implements IWitListener {
    EditText response;
    TextView chatView;
    Button submit;

    String dataInput;
    Wit wit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        response = (EditText)findViewById(R.id.response);
        chatView = (TextView)findViewById(R.id.chatView);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataInput = response.getEditableText().toString();
                response.setText("");
                wit.captureTextIntent(dataInput);
                chatView.append("Me: " + dataInput + "\n");
            }
        });
        wit = new Wit("UXMB3VI3YB63UFTUUQCWFSBU5JQ4UCBW", this);
    }

    @Override
    public void witDidGraspIntent(ArrayList<WitOutcome> arrayList, String s, Error error) {
        for (int i = 0; i < arrayList.size(); i++) {
            Response r = new Response(this, arrayList.get(i));
            chatView.append("Bot: " + r.process() + "\n");
        }
    }

    @Override
    public void witDidStartListening() {}

    @Override
    public void witDidStopListening() {}

    @Override
    public void witActivityDetectorStarted() {}

    @Override
    public String witGenerateMessageId() { return null; }
}
