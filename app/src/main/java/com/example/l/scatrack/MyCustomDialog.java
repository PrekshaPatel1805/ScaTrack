package com.example.l.scatrack;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class MyCustomDialog extends Dialog {
    public interface ReadyListener {
        public void ready(String name);
    }
    private String name;
    private ReadyListener readyListener;
    EditText etName;
    RatingBar ratestar;
    public MyCustomDialog(Context context, String name,
                          ReadyListener readyListener) {
        super(context);
        this.name = name;
        this.readyListener = readyListener;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_layout);
        setTitle("Enter your Name ");
        Button buttonOK = (Button) findViewById(R.id.Button01);
        buttonOK.setOnClickListener(new OKListener());
        ratestar = (RatingBar)findViewById(R.id.ratingbar_default);
        //etName = (EditText) findViewById(R.id.EditText01);
    }
    private class OKListener implements View.OnClickListener {
        public void onClick(View v)
        {
            //readyListener.ready(String.valueOf(etName.getText()));
            readyListener.ready(String.valueOf(ratestar.getRating()));
            MyCustomDialog.this.dismiss();
        }
    }
}