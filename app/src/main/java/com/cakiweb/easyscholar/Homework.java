package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Homework extends AppCompatActivity {
    LinearLayout noData;
    TextView home_id;
    RecyclerView recyclerView;
    HomeWorkAdapter homeWorkAdapter;
    ArrayList<HomeWorkData> homeWorkData=new ArrayList<HomeWorkData>();
    String id,stu_id,class_id,api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        home_id = findViewById(R.id.home_id);
        noData = findViewById(R.id.noDataHomework);
        noData.findViewById(R.id.noDataHomework).setVisibility(View.GONE);
        recyclerView=findViewById(R.id.homeworkRecycler);
        recyclerView.setHasFixedSize(true);

        homeWorkAdapter=new HomeWorkAdapter(homeWorkData,Homework.this);
        recyclerView.setAdapter(homeWorkAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Homework.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        for (int i=0; i<10; i++ ) {
//            HomeWorkData homeWorkData1=new HomeWorkData("20 Mar 2021","English","first","20-3-2021","first","15-3-2021","yes","Download the attachment","Home Work Submitted on Time");
//            homeWorkData.add(homeWorkData1);
//        }
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        class_id= sh.getString("class_id", "");
        api=sh.getString("api","");
        Intent intent = getIntent();
        id = intent.getStringExtra("val");
        if(id.equals("homework")){
            home_id.setText("Home Task");
        }else {
            home_id.setText("Class Task");
        }

        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=homework&userId="+id;
        String url = api+"student_id="+stu_id+"&method="+id+"&class_id="+class_id;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
                //Toast.makeText(Homework.this,""+response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Homework.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showJSON(String response) {

        // Toast.makeText(PaymentActivity.this,""+response,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {

            noData.findViewById(R.id.noDataHomework).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);
                    if(id.equals("homework")){
                        HomeWorkData homeWorkData1=new HomeWorkData(ob.getString("date")
                                ,ob.getString("Subject")
                                ,ob.getString("home_task")
                                ,ob.getString("date")
                                ,ob.getString("chapter")
                                ,"na","na","Download the attachment",ob.getString("attend_status"));
                        homeWorkData.add(homeWorkData1);
                    }
                    else {
                        HomeWorkData homeWorkData1=new HomeWorkData(ob.getString("date"),ob.getString("Subject"),ob.getString("class_task"),ob.getString("date"),ob.getString("chapter"),"na","na","Download the attachment",ob.getString("attend_status"));
                        homeWorkData.add(homeWorkData1);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Collections.reverse(homeWorkData);
            recyclerView.setAdapter(homeWorkAdapter);
            homeWorkAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataHomework).setVisibility(View.VISIBLE);
        }
    }
}