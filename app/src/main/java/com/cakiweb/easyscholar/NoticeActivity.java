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
import java.util.Collections;
import java.util.HashMap;

public class NoticeActivity extends AppCompatActivity {
    LinearLayout noData;
    RecyclerView recyclerView;
    NoticeAdapter noticeAdapter;
    ArrayList<NoticeData> noticeData=new ArrayList<NoticeData>();
    String HttpURL ;
    String id;
    String url = "http://erpdis.dooninternational.in/erp/index.php/Api_request/api_list?";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,class_id,session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        recyclerView =findViewById(R.id.noticeRecycler);
        recyclerView.setHasFixedSize(true);
        noData = findViewById(R.id.noDataNotification);
        noData.findViewById(R.id.noDataNotification).setVisibility(View.GONE);

        noticeAdapter=new NoticeAdapter(noticeData,NoticeActivity.this);
        recyclerView.setAdapter(noticeAdapter);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(NoticeActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        class_id =sh.getString("class_id","");
        HttpURL=sh.getString("api","");

//        for (int i=0; i<10; i++ ) {
//            NoticeData history=new NoticeData("notice_title","notice_description","created_on");
//            noticeData.add(history);
//        }


        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url = HttpURL+"method=notice&class_id="+class_id;

        Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        //String url = "https://cakitech.com/soumya/index.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
                //Toast.makeText(NoticeActivity.this,""+response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NoticeActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private void showJSON(String response) {

        //Toast.makeText(NoticeActivity.this,""+response+"  "+class_id,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {
            noData.findViewById(R.id.noDataNotification).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);
//                    NoticeData history = new NoticeData("Notice Id "+ob.getString("id"), ob.getString("notice"), " ");
//                    noticeData.add(history);

                    NoticeData history = new NoticeData(ob.getString("notice"),
                            ob.getString("notice_description"), ob.getString("enddate"),ob.getString("noticeURL"),ob.getString("id"));
                    noticeData.add(history);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

          Collections.reverse(noticeData);
            recyclerView.setAdapter(noticeAdapter);
            noticeAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataNotification).setVisibility(View.VISIBLE);
        }

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