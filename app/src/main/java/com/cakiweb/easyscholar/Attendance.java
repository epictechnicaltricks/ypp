package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Attendance extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout noData,linearLayout;
    DayAdapter dayAdapter;
    ArrayList<Day> days = new ArrayList<Day>();
    int k = 1, x = 0,a=0,p=0,t=0;
    String status="5";
    TextView attmonth,totalClass,abb,pre;
    JSONArray result;
    String url = "index.php/Api_request/api_list";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        noData = findViewById(R.id.noData_attandance);
        linearLayout = findViewById(R.id.lin_attandance);
        noData.findViewById(R.id.noData_attandance).setVisibility(View.GONE);
        linearLayout.findViewById(R.id.lin_attandance).setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.attendanceRecycler);
        recyclerView.setHasFixedSize(true);
        attmonth = findViewById(R.id.attMonth);
        totalClass = findViewById(R.id.totalClass);
        abb = findViewById(R.id.totalAbb);
        pre = findViewById(R.id.totalAtt);

        totalClass.setText(String.valueOf(t));
        abb.setText(String.valueOf(a));
        pre.setText(String.valueOf(p));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        dayAdapter = new DayAdapter(days, this);
        //dayAdapter.setClickListener(this);
        recyclerView.setAdapter(dayAdapter);

        SimpleDateFormat currentMonth = new SimpleDateFormat("M");
        Date todayMonth = new Date();
        String month = currentMonth.format(todayMonth);

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy");
        Date todayYear = new Date();
        String year = currentDate.format(todayYear);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String stu_id = sh.getString("student_id", "");
        api=sh.getString("api1","");
        url=api+url;

        UserLoginFunction(month,stu_id,year);


    }


    public void UserLoginFunction(final String month, final String id,final String year) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Attendance.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(Attendance.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if (httpResponseMsg.contains("200")) {
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        result = jsonObject.getJSONArray("resultSet");
                        int s = Integer.valueOf(jsonObject.getString("days"));
                        int w = Integer.valueOf(jsonObject.getString("week"));
                        //int w=7;
                        // Toast.makeText(Attandance.this,""+s,Toast.LENGTH_LONG).show();
                        attmonth.setText(jsonObject.getString("month"));
                        if(w==7){
                            for (int i = 1; i <= s; i++) {
                                for (int j = 0; j < result.length(); j++) {
                                    JSONObject ob = result.getJSONObject(j);
                                    if(ob.getString("dateD").equals(String.valueOf(i))){
                                        status=ob.getString("status");
                                        if(ob.getString("status").equals("0")){
                                            a++;
                                        }
                                        if(ob.getString("status").equals("1")){
                                            p++;
                                        }
                                        t++;
                                    }
                                }
                                Day history = new Day(status, String.valueOf(i), "", "", String.valueOf(x));
                                days.add(history);
                                x++;
                                status="5";
                            }
                            totalClass.setText(String.valueOf(t));
                            abb.setText(String.valueOf(a));
                            pre.setText(String.valueOf(p));

                        }
                        else {
                            Day history = new Day("", " ", "", "", "");
                            days.add(history);
                            for (int i = 1; i <= w - 1; i++) {
                                history = new Day("", " ", "", "", "");
                                days.add(history);
                                k++;
                            }

                            for (int i = 1; i <= s; i++) {
                                for (int j = 0; j < result.length(); j++) {
                                    JSONObject ob = result.getJSONObject(j);
                                    if (ob.getString("dateD").equals(String.valueOf(i))) {
                                        status = ob.getString("status");
                                        if(ob.getString("status").equals("0")){
                                            a++;
                                        }
                                        if(ob.getString("status").equals("1")){
                                            p++;
                                        }
                                        t++;
                                    }
                                }
                                history = new Day(status, String.valueOf(i), "", "", String.valueOf(k));
                                days.add(history);
                                k++;
                                status = "5";
                            }
                            totalClass.setText(String.valueOf(t));
                            abb.setText(String.valueOf(a));
                            pre.setText(String.valueOf(p));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(dayAdapter);
                    dayAdapter.notifyDataSetChanged();
                }
                else {
                    noData.findViewById(R.id.noData_attandance).setVisibility(View.VISIBLE);
                    linearLayout.findViewById(R.id.lin_attandance).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        int s = Integer.valueOf(jsonObject.getString("days"));
                        int w = Integer.valueOf(jsonObject.getString("week"));
                        // Toast.makeText(Attandance.this,""+s,Toast.LENGTH_LONG).show();
                        attmonth.setText(jsonObject.getString("month"));
                        if(w==7){
                            for (int i = 1; i <= s; i++) {
                                Day history = new Day(status, String.valueOf(i), "", "", String.valueOf(x));
                                days.add(history);
                                x++;
                                status="5";
                            }
                        }
                        else {
                            Day history = new Day("", " ", "", "", "");
                            days.add(history);
                            for (int i = 1; i <= w - 1; i++) {
                                history = new Day("", " ", "", "", "");
                                days.add(history);
                                k++;
                            }
                            for (int i = 1; i <= s; i++) {
                                history = new Day(status, String.valueOf(i), "", "", String.valueOf(k));
                                days.add(history);
                                k++;
                                status = "5";
                            }
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(dayAdapter);
                    dayAdapter.notifyDataSetChanged();
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {
                String jsonInputString = "{\"method\":\"student_attendance\",\"student_id\":\""+id+"\",\"month\":\""+month+"\",\"year\":\""+year+"\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
    }


    public void onBack(View view) {
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onPrevious(View view) {
    }

    public void onNext(View view) {
    }
}