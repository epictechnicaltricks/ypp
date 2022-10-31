package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    TextView animation;
    ImageView imageView;
    private static int splash_time=4000;
    boolean connected = false;
    boolean isConnected = false;
    RelativeLayout inter;
    CardView refresh;
    String logkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation = findViewById(R.id.animation);
        imageView = findViewById(R.id.animationImg);

        inter = findViewById(R.id.inter);
        refresh=findViewById(R.id.refresh);
        inter.findViewById(R.id.inter).setVisibility(View.VISIBLE);
        refresh.findViewById(R.id.refresh).setVisibility(View.GONE);

//        YoYo.with(Techniques.FadeIn)
//                .duration(1700)
//                .repeat(1)
//                .playOn(animation);
        YoYo.with(Techniques.SlideInLeft)
                .duration(1400)
                .repeat(1)
                .playOn(animation);
//        YoYo.with(Techniques.SlideOutRight)
//                .duration(1400)
//                .repeat(1)
//                .playOn(animation);

//        YoYo.with(Techniques.Landing)
//                .duration(1400)
//                .repeat(0)
//                .playOn(imageView);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        logkey=sh.getString("student_id","");

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
            inter.findViewById(R.id.inter).setVisibility(View.VISIBLE);
            refresh.findViewById(R.id.refresh).setVisibility(View.GONE);
        }
        else {
            connected = false;
            inter.findViewById(R.id.inter).setVisibility(View.GONE);
            refresh.findViewById(R.id.refresh).setVisibility(View.VISIBLE);
        }

        if(connected){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(logkey.equals("")){
                        finish();
                        //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        finish();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            },splash_time);
        }
        else {
            Toast.makeText(MainActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();

        }



    }

    public void onLogo(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            isConnected = true;
            inter.findViewById(R.id.inter).setVisibility(View.VISIBLE);
            refresh.findViewById(R.id.refresh).setVisibility(View.GONE);
        }
        else {
            isConnected = false;
            inter.findViewById(R.id.inter).setVisibility(View.GONE);
            refresh.findViewById(R.id.refresh).setVisibility(View.VISIBLE);
        }

        if(isConnected){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(logkey.equals("")){
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        finish();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            },splash_time);
        }
        else {
            Toast.makeText(MainActivity.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

}