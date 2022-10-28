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

public class ResultActivity extends AppCompatActivity {
    LinearLayout noData;
    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    ArrayList<ResultData> resultData = new ArrayList<ResultData>();
    String stu_id,api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView = findViewById(R.id.resultRecycler);
        noData = findViewById(R.id.noDataResult);
        noData.findViewById(R.id.noDataResult).setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);

        resultAdapter = new ResultAdapter(resultData, ResultActivity.this);
        recyclerView.setAdapter(resultAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ResultActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        api=sh.getString("api","");

//        for (int i=0; i<10; i++ ) {
//           ResultData history=new ResultData("notice_title","notice_description","created_on");
//            resultData.add(history);
//        }
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        //String url = HttpURL+"?method=homework&userId="+id;
        String url = api+"&method=examresult"+"&student_id="+stu_id;

        Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(url, response -> {

            loading.dismiss();
            showJSON(response);
            //Toast.makeText(Homework.this,""+response,Toast.LENGTH_LONG).show();
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResultActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {

        // Toast.makeText(PaymentActivity.this,""+response,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {

            noData.findViewById(R.id.noDataResult).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);

                    ResultData Data1=new ResultData(

                            ob.getString("class_name"),
                            ob.getString("exam_term"),
                            ob.getString("grade"),
                            ob.getString("session_name"),
                            ob.getString("teacher_remarks"),
                            ob.getString("author")
                    );


                    resultData.add(Data1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Collections.reverse(resultData);
            recyclerView.setAdapter(resultAdapter);
            resultAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataResult).setVisibility(View.VISIBLE);
        }
    }












    public void onBack(View view) {
      onBackPressed();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}