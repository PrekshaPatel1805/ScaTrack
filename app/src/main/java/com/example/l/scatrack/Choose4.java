package com.example.l.scatrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Choose4 extends AppCompatActivity {
    ImageButton bo,client;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose4);
        bo= (ImageButton) findViewById(R.id.boimagebutton);
        client= (ImageButton) findViewById(R.id.clientimagebutton);
        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Choose4.this,BoIntroSlider.class);
                startActivity(intent);
            }
        });
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(Choose4.this,ClientIntroSlider.class);
                startActivity(intent);
            }
        });
    }
}
