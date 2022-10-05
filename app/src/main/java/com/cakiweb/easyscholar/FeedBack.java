package com.cakiweb.easyscholar;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class FeedBack extends AppCompatActivity {
LinearLayout com,sta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        com = findViewById(R.id.comp);
        sta = findViewById(R.id.compSta);
        com.findViewById(R.id.comp).setVisibility(View.VISIBLE);
        sta.findViewById(R.id.compSta).setVisibility(View.GONE);
    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onSubmit(View view) {
    }

    public void onStatus(View view) {
        com.findViewById(R.id.comp).setVisibility(View.GONE);
        sta.findViewById(R.id.compSta).setVisibility(View.VISIBLE);
    }
}