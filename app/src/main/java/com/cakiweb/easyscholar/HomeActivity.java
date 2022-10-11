package com.cakiweb.easyscholar;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView name;
    String strName="s",tockenFcm,stu_id="1",api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawer = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.nav_drop);
        name=findViewById(R.id.studentName);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // toggle.setDrawerIndicatorEnabled(false);
       // toolbar.setNavigationIcon(R.drawable.menu);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.doon_icon);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), BasicInformation.class));
                        break;
                    case R.id.nav_fee:
                        startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                        // Toast.makeText(HomeActivity.this, "photo !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_result:
                        //startActivity(new Intent(getApplicationContext(), MarkSheet.class));
                        Toast.makeText(HomeActivity.this, "result !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_About:
                        //startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        Toast.makeText(HomeActivity.this, "about !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_privacy:
                       // startActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));
                        Toast.makeText(HomeActivity.this, "privacy !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_terms:
                        //startActivity(new Intent(getApplicationContext(), TermsAndCondition.class));
                         Toast.makeText(HomeActivity.this, "terms !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_contact:
                        //startActivity(new Intent(getApplicationContext(), ContactUs.class));
                        Toast.makeText(HomeActivity.this, "contact !", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(HomeActivity.this);
                        builder2.setMessage("Are you sure you want to Logout?");
                        builder2.setCancelable(true);

                        builder2.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        SharedPreferences sha = HomeActivity.this.getSharedPreferences("MySharedPref",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sha.edit();
                                        editor.clear();
                                        editor.apply();
                                                                                Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });

                        builder2.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert1 = builder2.create();
                        alert1.show();
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        strName =sh.getString("student_name","");
        stu_id=sh.getString("student_id","");
        api=sh.getString("api","");
        name.setText(strName);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "Done";
//                        if (!task.isSuccessful()) {
//                            msg = "Failed";
//                        }
//
//                    }
//                });
//
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        tockenFcm =  token;
//                        Log.d(TAG,"Token: "+ tockenFcm);
//                        log();
//
//                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

    }
    public void log(){
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=exam&userId="+id;
        //String url = api+"method=push_notice&mobile=4354545456&student_id="+stu_id+"&device_id="+tockenFcm;
        String url = api+"method=push_notice&student_id="+stu_id+"&device_id="+tockenFcm+"&mobile=43545454";
        //Toast.makeText(HomeActivity.this, url, Toast.LENGTH_SHORT).show();
        Log.d(TAG,"url: "+ url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String httpResponseMsg) {
                loading.dismiss();
                //showJSON(response);
                //Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                if (httpResponseMsg.contains("200")) {
                    Toast.makeText(HomeActivity.this,"success",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(HomeActivity.this,"success",Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onBasicInfo(View view) {
        Intent intent=new Intent(HomeActivity.this,BasicInformation.class);
        startActivity(intent);
    }
    public void onMarkSheet(View view) {
        Intent intent=new Intent(HomeActivity.this,ResultActivity.class);
        startActivity(intent);
        //Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onTimetable(View view) {
        //Intent intent=new Intent(HomeActivity.this,TimeTable.class);
        //startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onCalender(View view) {
        //Intent intent=new Intent(HomeActivity.this,TimeTable.class);
        //startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onCca_result(View view) {
        //Intent intent=new Intent(HomeActivity.this,TimeTable.class);
        //startActivity(intent);

        startActivity(new Intent(this,Cca_result_Activity.class));

      
        //Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onComplain(View view) {
        //Intent intent=new Intent(HomeActivity.this,TimeTable.class);
        //startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }


    public void onExamTimeTable(View view) {
        Intent intent=new Intent(HomeActivity.this,ExamTimeTable.class);
       startActivity(intent);
       // Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onPayment(View view) {
        Intent intent=new Intent(HomeActivity.this,PaymentActivity.class);
        startActivity(intent);
    }

    public void onNotices(View view) {
       Intent intent=new Intent(HomeActivity.this, NoticeActivity.class);
        startActivity(intent);
      //  Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }


    public void onStudyMaterial(View view) {
//        Intent intent=new Intent(HomeActivity.this, StudyMaterial.class);
//        startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onLibrary(View view) {
//        Intent intent=new Intent(HomeActivity.this, LibraryActivity.class);
//        startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onFeedback(View view) {
//        Intent intent=new Intent(HomeActivity.this, FeedBack.class);
//        startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onLeave(View view) {
        Intent intent=new Intent(HomeActivity.this, LeaveApplication.class);
        startActivity(intent);
    }

    public void onAttendance(View view) {
        Intent intent=new Intent(HomeActivity.this, Attendance.class);
        startActivity(intent);
    }

    public void onEvents(View view) {
        Intent intent=new Intent(HomeActivity.this, EventActivity.class);
        startActivity(intent);
    }

    public void onTransport(View view) {
        Intent intent=new Intent(HomeActivity.this, Transport.class);
        startActivity(intent);
    }

    public void onCanteen(View view) {
//        Intent intent=new Intent(HomeActivity.this, Canteen.class);
//        startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onHostel(View view) {
//        Intent intent=new Intent(HomeActivity.this, Hostel.class);
//        startActivity(intent);
        Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }

    public void onClassTask(View view) {
        Intent intent=new Intent(HomeActivity.this,Homework.class);
        intent.putExtra("val","classwork");
        startActivity(intent);
        //Toast.makeText(HomeActivity.this, "on progress", Toast.LENGTH_SHORT).show();
    }
    public void onHomeWork(View view) {
        Intent intent=new Intent(HomeActivity.this,Homework.class);
        intent.putExtra("val","homework");
        startActivity(intent);
    }
    public void onOnline_Class(View view) {
        Intent intent=new Intent(HomeActivity.this,TimeTable.class);
        startActivity(intent);
    }
}