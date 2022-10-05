package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

public class TimeTable extends AppCompatActivity {
    View mon,tue,wed,thu,fri,sat;

    RecyclerView recyclerView;
    TimeTableAdapter timeTableAdapter;
    ArrayList<Period> periods=new ArrayList<Period>();
    LinearLayout noData;
    String stu_id,session,api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
//        mon = findViewById(R.id.clickMon);
//        tue = findViewById(R.id.clickTue);
//        wed = findViewById(R.id.clickWed);
//        thu = findViewById(R.id.clickThu);
//        fri = findViewById(R.id.clickFri);
//        sat = findViewById(R.id.clickSat);
//        mon.findViewById(R.id.clickMon).setVisibility(View.VISIBLE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);

        noData = findViewById(R.id.noDataTimeTable);
        noData.findViewById(R.id.noDataTimeTable).setVisibility(View.GONE);
        recyclerView=findViewById(R.id.timeTableRecycler);
        recyclerView.setHasFixedSize(true);

        timeTableAdapter=new TimeTableAdapter(periods,TimeTable.this);
        recyclerView.setAdapter(timeTableAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TimeTable.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        session = sh.getString("class_id", "");
        api=sh.getString("api","");


//        for (int i=0; i<10; i++ ) {
//
//            Period period=new Period("09.00-09.30","English","soumya","20-30","first");
//            periods.add(period);
//        }
        log("onlineclass");

    }

    public void log(final String method){
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=exam&userId="+id;
        String url = api+"method="+method+"&student_id="+stu_id+"&class_id="+session;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String httpResponseMsg) {
                loading.dismiss();
                //showJSON(response);
                //Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                if (httpResponseMsg.contains("200")) {

                    noData.findViewById(R.id.noDataTimeTable).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            Period period=new Period(ob.getString("date"),ob.getString("Subject"),"Tought by "+ob.getString("Added By"),ob.getString("notice"),"please click on link");
                            periods.add(period);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(timeTableAdapter);
                    timeTableAdapter.notifyDataSetChanged();
                } else {
                    noData.findViewById(R.id.noDataTimeTable).setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TimeTable.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void onBack(View view) {
        finish();
    }

//    public void onMonday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.VISIBLE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//
//            Period period=new Period("09.00-09.30","English","soumya","20-30","first");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
//
//
//
//    public void onTuesday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.GONE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.VISIBLE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//            Period period=new Period("09.00-09.30","Math","soumya","20-30","2.6 activity");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
//
//    public void onWednesday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.GONE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.VISIBLE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//            Period period=new Period("09.00-09.30","MIL","soumya","20-30","2.6 activity");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
//
//    public void onThursday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.GONE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.VISIBLE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//            Period period=new Period("09.00-09.30","geography","soumya","20-30","2.6 activity");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
//
//    public void onFriday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.GONE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.VISIBLE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.GONE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//            Period period=new Period("09.00-09.30","History","soumya","20-30","2.6 activity");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
//
//    public void onSaturday(View view) {
//        mon.findViewById(R.id.clickMon).setVisibility(View.GONE);
//        tue.findViewById(R.id.clickTue).setVisibility(View.GONE);
//        wed.findViewById(R.id.clickWed).setVisibility(View.GONE);
//        thu.findViewById(R.id.clickThu).setVisibility(View.GONE);
//        fri.findViewById(R.id.clickFri).setVisibility(View.GONE);
//        sat.findViewById(R.id.clickSat).setVisibility(View.VISIBLE);
//        periods.clear();
//        timeTableAdapter.notifyDataSetChanged();
//        for (int i=0; i<10; i++ ) {
//            Period period=new Period("09.00-09.30","science","soumya","20-30","2.6 activity");
//            periods.add(period);
//        }
//        recyclerView.setAdapter(timeTableAdapter);
//        timeTableAdapter.notifyDataSetChanged();
//    }
}