package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EventActivity extends AppCompatActivity {

    LinearLayout noData;
    RecyclerView recyclerView;
    EventAdapter eventAdapter;
    ArrayList<NoticeData> eventData=new ArrayList<NoticeData>();
    String url = "index.php/Api_request/api_list";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,class_id,date,to,from,api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        recyclerView =findViewById(R.id.eventRecycler);
        recyclerView.setHasFixedSize(true);
        noData = findViewById(R.id.noDataEvent);
        noData.findViewById(R.id.noDataEvent).setVisibility(View.GONE);

        eventAdapter=new EventAdapter(eventData,EventActivity.this);
        recyclerView.setAdapter(eventAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(EventActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        date = df.format(c.getTime());
        to=date+"-01-01";
        from=date+"-12-01";

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        api=sh.getString("api1","");
        url=api+url;

        //Toast.makeText(EventActivity.this,date,Toast.LENGTH_SHORT).show();
//        for (int i=0; i<10; i++ ) {
//            NoticeData history=new NoticeData("Annual Sports","participation is mandatory","02 apr 2021");
//            eventData.add(history);
//        }
        UserLoginFunction();
    }

    public void UserLoginFunction() {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(EventActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
               // Toast.makeText(EventActivity.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if (httpResponseMsg.contains("200")) {

                    noData.findViewById(R.id.noDataEvent).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            NoticeData history=new NoticeData(ob.getString("title"),ob.getString("start")+" to "+ob.getString("end"),ob.getString("type"),"","");
                            eventData.add(history);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recyclerView.setAdapter(eventAdapter);
                    eventAdapter.notifyDataSetChanged();
                } else {
                    noData.findViewById(R.id.noDataEvent).setVisibility(View.VISIBLE);

                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {
                String jsonInputString = "{\"method\":\"eventholiday\",\"from_date\":\"2020-01-01\",\"to_date\":\"2021-12-01\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
    }
    public void onBack(View view) {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}